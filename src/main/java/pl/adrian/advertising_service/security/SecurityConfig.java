package pl.adrian.advertising_service.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final ObjectMapper objectMapper;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final String secret;

    public SecurityConfig(DataSource dataSource, ObjectMapper objectMapper, AuthenticationSuccessHandler successHandler,
                          AuthenticationFailureHandler failureHandler, @Value("${jwt.secret}") String secret) {
        this.dataSource = dataSource;
        this.objectMapper = objectMapper;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.secret = secret;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        // Test user
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withUser("adrian")
                .password("{bcrypt}" + new BCryptPasswordEncoder().encode("adrian"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/advertisements").permitAll()
                    .antMatchers(HttpMethod.GET, "/advertisements/*").permitAll()
                    .antMatchers(HttpMethod.GET, "/categories").permitAll()
                    .antMatchers(HttpMethod.GET, "/categories/*").permitAll()
                    .antMatchers(HttpMethod.GET, "/addresses").permitAll()
                    .antMatchers(HttpMethod.GET, "/addresses/*").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                    .and()
                .addFilter(authenticationFilter())
                .addFilter(new AuthenticationJwtFilter(authenticationManager(), userDetailsManager(), secret))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    public AuthenticationFilter authenticationFilter() throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(objectMapper);
        authenticationFilter.setAuthenticationSuccessHandler(successHandler);
        authenticationFilter.setAuthenticationFailureHandler(failureHandler);
        authenticationFilter.setAuthenticationManager(super.authenticationManager());
        return authenticationFilter;
    }

    @Bean
    public UserDetailsManager userDetailsManager(){
        return new JdbcUserDetailsManager(dataSource);
    }
}
