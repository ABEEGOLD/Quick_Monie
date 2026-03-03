package org.quickmonie.core.security.provider;

import lombok.AllArgsConstructor;
import org.quickmonie.core.security.auth.CustomAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuickMonieAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String userPassword = userDetails.getPassword();
        if(!passwordEncoder.matches(password, userPassword))
            throw new BadCredentialsException("invalid authentication credentials supplied");
        return new CustomAuthentication(username, null, userDetails.getAuthorities(), true);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class == authentication;
    }
}
