package com.epam.esm;

import com.epam.esm.filters.FilterChainExceptionHandler;
import com.epam.esm.filters.JwtRequestFilter;
import com.epam.esm.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private JwtRequestFilter jwtRequestFilter;
    private FilterChainExceptionHandler filterChainExceptionHandler;

    @Autowired
    public void setUserDetailsService(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void setFilterChainExceptionHandler(FilterChainExceptionHandler filterChainExceptionHandler) {
        this.filterChainExceptionHandler = filterChainExceptionHandler;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().and()

                .authorizeRequests()

                .antMatchers("/authenticate", "/signup").permitAll()

                .antMatchers(HttpMethod.GET, "/certificates/**").permitAll()
                .antMatchers("/certificates/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/tags/**").hasAnyRole("USER", "ADMIN")
                .antMatchers( "/tags/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/users/*").hasAnyRole("USER", "ADMIN")
                .antMatchers("/users/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/orders/*", "/orders/*/order").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/orders").hasAnyRole("USER", "ADMIN")
                .antMatchers("/orders/**").hasRole("ADMIN")

                .antMatchers("/**").permitAll()

                .anyRequest().authenticated()

                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterChainExceptionHandler, JwtRequestFilter.class);
    }
}
