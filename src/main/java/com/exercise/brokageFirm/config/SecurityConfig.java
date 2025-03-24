package com.exercise.brokageFirm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
class SecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        new AntPathRequestMatcher("/h2-console/**"),
                        new AntPathRequestMatcher("/assets/**"),
                        new AntPathRequestMatcher("/orders/**"),
                        new AntPathRequestMatcher("/customers/**"),
                        new AntPathRequestMatcher("/trades/**"),
                        new AntPathRequestMatcher("/admin/**")
                );
    }
}