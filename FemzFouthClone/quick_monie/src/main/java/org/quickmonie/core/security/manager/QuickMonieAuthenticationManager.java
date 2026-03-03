package org.quickmonie.core.security.manager;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quickmonie.core.security.exception.AuthenticationNotSupportedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class QuickMonieAuthenticationManager implements AuthenticationManager {
    private final AuthenticationProvider authenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authenticationProvider.supports(authentication.getClass())) {
            return authenticationProvider.authenticate(authentication);
        }
        throw new AuthenticationNotSupportedException("authentication type not supported by server");
    }
}

