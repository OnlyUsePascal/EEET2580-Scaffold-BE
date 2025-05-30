package com.example.test2.common.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.test2.auth.AuthRequestFilter;
import com.example.test2.common.enums.UserRole;

@Configuration
@EnableWebSecurity
// @EnableMethodSecurity
public class SecurityConfiguration {
  @Autowired
  private AuthRequestFilter authRequestFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(req -> {
          var conf = new CorsConfiguration();
          conf.setAllowedOrigins(List.of("*"));
          conf.setAllowedHeaders(List.of("*"));
          conf.setAllowedMethods(List.of("*"));
          conf.setAllowCredentials(true);
          return conf;
        }))
        .authorizeHttpRequests(req -> req
            .requestMatchers(
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/error",
                "/auth/sign-in", "/auth/sign-up")
            .permitAll()
            // .requestMatchers("/stat").hasRole(
            // UserRole.STAFF.getName()) // if role -> "ROLE_" + role !!!
            .requestMatchers("/stat").hasAuthority(UserRole.ADMIN.getName())
            .anyRequest().authenticated())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(authRequestFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }
}
