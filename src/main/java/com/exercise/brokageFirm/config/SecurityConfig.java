package com.exercise.brokageFirm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/customers/register", "/customers/login", "/h2-console/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // basit login için
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // Test amaçlı memory'de user tutuyoruz (dilersen gerçek DB entegrasyonu da ekleriz)
        UserDetails admin = User.withUsername("admin")
                .password("{noop}password") // {noop} şifre encoding'i yapmadan kabul eder
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}