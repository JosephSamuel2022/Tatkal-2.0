package com.tatkal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    JwtFilter jwtFilter;

    /*
    * Disable CSRF Protection
    * Allow login without authentication
    * Set session management to stateless
    * Configure JWT filter
    */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
          .requestMatchers("/tatkal/login").permitAll()
          .anyRequest().authenticated()
        )
        .sessionManagement(session ->
          session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

      http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
      return http.build();
    }

}
