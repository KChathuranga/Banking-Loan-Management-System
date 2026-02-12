package com.bank.loan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Public endpoints
                        .requestMatchers("/api/auth/**", "/swagger-ui/**",
                                "/v3/api-docs/**").permitAll()

                        // Customer endpoints
                        .requestMatchers("/api/loans").hasRole("CUSTOMER")
                        .requestMatchers("/api/loans/emis/**").hasRole("CUSTOMER")

                        // Loan officer endpoints
                        .requestMatchers("/api/loans/*/approve").hasRole("LOAN_OFFICER")
                        .requestMatchers("/api/loans/*/reject").hasRole("LOAN_OFFICER")

                        // Admin endpoints
                        .requestMatchers("/api/loans").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
