package com.agriconnect.config;

import com.agriconnect.security.JwtAuthenticationFilter;
import com.agriconnect.security.JwtService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService
    ) {
        this.jwtService = jwtService;
        this.customUserDetailsService =
                customUserDetailsService;
    }

    // ========================================
    // JWT AUTHENTICATION FILTER
    // ========================================

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {

        return new JwtAuthenticationFilter(
                jwtService,
                customUserDetailsService
        );
    }

    // ========================================
    // SECURITY FILTER CHAIN
    // ========================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // ========================================
                // CSRF
                // ========================================

                .csrf(csrf ->
                        csrf.disable()
                )

                // ========================================
                // CORS
                // ========================================

                .cors(Customizer.withDefaults())

                // ========================================
                // SESSION
                // ========================================

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // ========================================
                // AUTHORIZATION
                // ========================================

                .authorizeHttpRequests(auth -> auth

                        // ------------------------------------
                        // CORS PREFLIGHT
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()

                        // ------------------------------------
                        // PUBLIC USER ENDPOINTS
                        // ------------------------------------

                        .requestMatchers(
                                "/api/users/login",
                                "/api/users/register",
                                "/api/users/test",
                                "/api/users/stats"
                        ).permitAll()

                        // ------------------------------------
                        // PUBLIC PESTICIDE ENDPOINTS
                        // ------------------------------------

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/pesticides",
                                "/api/pesticides/**"
                        ).permitAll()

                        // ------------------------------------
                        // EVERYTHING ELSE
                        // ------------------------------------

                        .anyRequest().authenticated()
                )

                // ========================================
                // JWT FILTER
                // ========================================

                .addFilterBefore(
                        jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )

                // ========================================
                // BASIC AUTHENTICATION
                // ========================================

                .httpBasic(
                        Customizer.withDefaults()
                );

        return http.build();
    }

    // ========================================
    // PASSWORD ENCODER
    // ========================================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}