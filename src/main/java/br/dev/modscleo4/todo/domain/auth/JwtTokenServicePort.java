package br.dev.modscleo4.todo.domain.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenServicePort {
    OauthInfo authenticate(UserDetails user);

    OauthInfo refresh(String refreshTokenString);

    Jws<Claims> getClaimsFromSignedToken(String token);
}
