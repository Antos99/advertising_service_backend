package pl.adrian.advertising_service.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationJwtFilter authenticationJwtFilter;
    private final FilterChainExceptionHandler filterChainExceptionHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, apiPrefix+"/advertisements/**").permitAll()
                .antMatchers(HttpMethod.POST, apiPrefix+"/advertisements").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.PUT, apiPrefix+"/advertisements/*").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.DELETE, apiPrefix+"/advertisements/*").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET, apiPrefix+"/categories/**").permitAll()
                .antMatchers(HttpMethod.POST, apiPrefix+"/categories").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, apiPrefix+"/categories/*").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, apiPrefix+"/categories/*").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, apiPrefix+"/addresses/**").permitAll()
                .antMatchers(HttpMethod.POST, apiPrefix+"/login").permitAll()
                .antMatchers(HttpMethod.POST, apiPrefix+"/register").permitAll()
                .antMatchers(HttpMethod.GET, apiPrefix+"/users").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, apiPrefix+"/users/*").permitAll()
                .antMatchers(HttpMethod.POST, apiPrefix+"/users").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, apiPrefix+"/users/**").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.PUT, apiPrefix+"/users/*/edit/roles").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, apiPrefix+"/users/*/edit/enabled").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, apiPrefix+"/users/*").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET, apiPrefix+"/roles").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(filterChainExceptionHandler, AuthenticationJwtFilter.class);
        http.exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(authenticationFailureHandler);

        return http.build();
    }
}
