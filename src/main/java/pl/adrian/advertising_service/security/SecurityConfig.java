package pl.adrian.advertising_service.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import pl.adrian.advertising_service.user.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final String secret;
    private final UserService userService;

    public SecurityConfig(ObjectMapper objectMapper, AuthenticationSuccessHandler successHandler,
                          AuthenticationFailureHandler failureHandler, @Value("${jwt.secret}") String secret,
                          UserService userService) {
        this.objectMapper = objectMapper;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.secret = secret;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/advertisements**").permitAll()
                .antMatchers(HttpMethod.POST, "/advertisements").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.PUT, "/advertisements").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.DELETE, "/advertisements/*").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET, "/categories**").permitAll()
                .antMatchers(HttpMethod.POST, "/categories").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/categories").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/categories/*").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/addresses**").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/users**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/users").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/roles**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/roles").hasAnyAuthority("ROLE_ADMIN");
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        http.addFilter(authenticationFilter())
                .addFilter(new AuthenticationJwtFilter(authenticationManager(), userService, secret));
    }

    public AuthenticationFilter authenticationFilter() throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(objectMapper);
        authenticationFilter.setAuthenticationSuccessHandler(successHandler);
        authenticationFilter.setAuthenticationFailureHandler(failureHandler);
        authenticationFilter.setAuthenticationManager(super.authenticationManager());
        return authenticationFilter;
    }

}
