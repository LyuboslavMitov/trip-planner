package com.blog.springhomework1.config;

import com.blog.springhomework1.domain.UsersService;
import com.blog.springhomework1.exception.InvalidCredentialsException;
import com.blog.springhomework1.exception.NonexisitngEntityException;
import com.blog.springhomework1.security.RestAuthenticationEntryPoint;
import com.blog.springhomework1.security.RestSavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/info").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger*/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").authenticated()
//                .antMatchers(HttpMethod.POST, "/api/users").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/**").hasAnyRole("BLOGGER", "ADMIN")
                .antMatchers(HttpMethod.PUT).hasAnyRole("BLOGGER", "ADMIN")
                .antMatchers(HttpMethod.DELETE).hasAnyRole("BLOGGER", "ADMIN")
                .and()
                .formLogin()
                .loginProcessingUrl("/api/login")
                .permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout()
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.ACCEPTED));

//                .and()
//                    .rememberMe();
    }
    @Bean
    public UserDetailsService userDetailsService(UsersService usersService) {
        return username -> {
            try {
                return usersService.findByUsername(username);
            } catch (NonexisitngEntityException ex) {
                throw new UsernameNotFoundException(ex.getMessage(), ex);
            }
        };
    }

}
