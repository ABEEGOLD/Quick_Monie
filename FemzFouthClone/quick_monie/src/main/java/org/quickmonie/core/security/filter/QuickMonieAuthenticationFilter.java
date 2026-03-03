package org.quickmonie.core.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.quickmonie.core.security.auth.CustomAuthentication;
import org.quickmonie.core.security.dto.request.LoginRequest;
import org.quickmonie.core.security.dto.response.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
@Primary
public class QuickMonieAuthenticationFilter extends OncePerRequestFilter {
    private final ObjectMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final String signingKey;

    public QuickMonieAuthenticationFilter(ObjectMapper mapper, AuthenticationManager authenticationManager, @Value("${jwt.signing.key}") String signingKey) {
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.signingKey = signingKey;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String requestPath = request.getServletPath();// /api/v1/auth/login
        if (!requestPath.equals("/api/v1/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        //1. extract auth credentials from request
        InputStream inputStream = request.getInputStream();
        LoginRequest loginRequest = mapper.readValue(inputStream, LoginRequest.class); //{"username":"", "password":""}
        //2. send request to AuthenticationManager
        Authentication authentication = new CustomAuthentication(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authResult = authenticationManager.authenticate(authentication);
       //TODO: generate jwt
        List<? extends GrantedAuthority> authorities = authResult.getAuthorities().stream().toList();
        String jwt = JWT.create()
                       .withSubject(loginRequest.getUsername())
                       .withIssuer("quickmonie")
//                       .withClaim("roles", authorities)
                       .withIssuedAt(Date.from(Instant.now()))
                       .withExpiresAt(Date.from(Instant.now().plusSeconds(86400)))
                       .sign(Algorithm.HMAC256(signingKey));

        LoginResponse loginResponse = new LoginResponse(jwt);
        response.setContentType("application/json");
        response.getOutputStream()
                .write(mapper.writeValueAsBytes(loginResponse));
        response.flushBuffer();
        filterChain.doFilter(request, response);
    }
}
