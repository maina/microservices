package com.crotontech.common.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletResponse;


@Configuration
@Order(1)
@Profile("microservices-security")
@EnableWebSecurity(debug = true)   // Enable security config. This annotation denotes config for spring security.
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtConfig jwtConfig;
    protected Log logger = LogFactory.getLog(this.getClass());

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("SecurityTokenConfig++++++");
        http
                .csrf().disable()
                .formLogin().disable()

        // make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // authorization requests config
                .authorizeRequests()
                // allow all who are accessing "auth" service
                .antMatchers(HttpMethod.POST, jwtConfig.getLoginUri()).permitAll()
                .antMatchers(HttpMethod.POST, jwtConfig.getRefreshTokenUri()).permitAll()
                // must be an admin if trying to access admin area (authentication is also required here)
                //for other uris
                //   .antMatchers(HttpMethod.GET, "/v1/**").hasRole("USER")
                // Any other request must be authenticated
                .anyRequest().authenticated()
                .and()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                // Add a filter to validate the tokens with every request
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new JwtAuthenticationEntryPoint();
    }
}