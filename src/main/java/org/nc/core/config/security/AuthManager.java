package org.nc.core.config.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthManager implements ReactiveAuthenticationManager {
    private final TokenProvider tokenProvider;

    public AuthManager(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        var authToken = authentication.getCredentials().toString();
        String username;
        try {
            username = tokenProvider.getUsernameFromToken(authToken);
        } catch (Exception e) {
            username = null;
        }
        if (username != null && ! tokenProvider.isTokenExpired(authToken)) {
            if(tokenProvider.isTokenExpired(authToken)) return Mono.empty();

            Claims claims = tokenProvider.getAllClaimsFromToken(authToken);
            List<String> roles = (List<String>) claims.get("scopes");
            List<SimpleGrantedAuthority> authorities =
                    roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, username, authorities);
            SecurityContextHolder.getContext().setAuthentication(new AuthenticatedUser(username, authorities));
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }

}


