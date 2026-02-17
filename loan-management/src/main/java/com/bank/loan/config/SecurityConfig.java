package com.bank.loan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/loans").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/loans/*/emis").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/api/loans/emis/*/pay").hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.PUT, "/api/loans/*/approve").hasRole("LOAN_OFFICER")
                        .requestMatchers(HttpMethod.PUT, "/api/loans/*/reject").hasRole("LOAN_OFFICER")
                        // LOAN OFFICER
                        .requestMatchers(HttpMethod.GET, "/api/loans/officer").hasRole("LOAN_OFFICER")

                        .requestMatchers(HttpMethod.GET, "/api/loans").hasRole("ADMIN")
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
