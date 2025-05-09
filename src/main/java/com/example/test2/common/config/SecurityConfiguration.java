package com.example.test2.common.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
    private final AuthRequestFilter authRequestFilter;

    @Autowired
    public SecurityConfiguration(AuthRequestFilter authRequestFilter) {
        this.authRequestFilter = authRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(req -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:5173")); // Allow frontend origin
                    config.setAllowedMethods(List.of("*")); // Allow specific HTTP methods
                    config.setAllowedHeaders(List.of("*")); // Allow all headers
                    config.setAllowCredentials(true); // Allow credentials (e.g., cookies)
                    return config;
                }))
                .authorizeHttpRequests(req -> req
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/error",
                                "/auth/sign-in", "/auth/sign-up")
                        .permitAll()
                        .requestMatchers("/stat").hasAuthority(UserRole.ADMIN.getName())
//                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/**").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/**").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/**").permitAll()
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
