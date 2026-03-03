package org.quickmonie.core.security.config;

import org.quickmonie.core.dto.response.AccountResponse;
import org.quickmonie.core.exception.AccountNotFoundException;
import org.quickmonie.core.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

@Configuration
public class UserDetailsServiceConfig {

    @Bean
    public UserDetailsService userDetailsService(AccountService accountService) {
        return (username)->getUserFromAccountStore(accountService, username);
    }

    private static User getUserFromAccountStore(AccountService accountService, String username) {
        try {
            AccountResponse response = accountService.getBy(username);
            List<SimpleGrantedAuthority> authorities = response.getAuthorities().stream()
                    .map(authority ->
                            new SimpleGrantedAuthority(authority.name()))
                    .toList();
            return new User(response.getEmail(), response.getPassword(), authorities);
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
