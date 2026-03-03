package org.quickmonie.core.security.config;

import lombok.AllArgsConstructor;
import org.quickmonie.core.security.filter.QuickMonieAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final OncePerRequestFilter oncePerRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(c->c.disable())
                .addFilterAt(oncePerRequestFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests((c)->c.requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll())
                .authorizeHttpRequests((c)->c.anyRequest().authenticated())
                .build();
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("password"));
    }
}
