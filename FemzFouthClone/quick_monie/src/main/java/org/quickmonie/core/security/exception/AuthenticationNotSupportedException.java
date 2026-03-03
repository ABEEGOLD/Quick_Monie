package org.quickmonie.core.security.exception;

public class AuthenticationNotSupportedException extends RuntimeException {
    public AuthenticationNotSupportedException(String message) {
        super(message);
    }
}
