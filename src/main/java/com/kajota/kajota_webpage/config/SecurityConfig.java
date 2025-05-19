package com.kajota.kajota_webpage.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    @Value("${crossOrigins}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll();
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Define allowed origins
        if(!allowedOrigins.isEmpty()){
            List<String> origins = Arrays.stream(allowedOrigins.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            corsConfig.setAllowedOrigins(origins);
            log.info("Allowed origins are : {}", allowedOrigins);
        }


        // Define allowed methods and headers
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "x-affiliate-code", "x-language-code"));
        corsConfig.setAllowCredentials(true);

        // Set max age
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }
}
