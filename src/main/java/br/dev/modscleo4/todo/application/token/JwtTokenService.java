package br.dev.modscleo4.todo.application.token;

import br.dev.modscleo4.todo.domain.auth.InvalidCredentialsException;
import br.dev.modscleo4.todo.domain.auth.OauthInfo;
import br.dev.modscleo4.todo.domain.auth.Token;
import br.dev.modscleo4.todo.domain.auth.TokenType;
import br.dev.modscleo4.todo.domain.user.User;
import br.dev.modscleo4.todo.infrastructure.configuration.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenService {
    private final TokenRepository tokenRepository;
    private final JwtConfiguration jwtConfiguration;

    @Transactional
    public OauthInfo authenticate(UserDetails user) {
        try (var _ = MDC.putCloseable("sub", user.getUsername())) {
            log.info("Authenticating user...");

            var accessToken = this.persistAccessToken((User) user);
            var refreshToken = this.persistRefreshToken(accessToken);

            return new OauthInfo(
                "Bearer",
                this.generateAccessToken(accessToken),
                this.generateRefreshToken(refreshToken),
                this.jwtConfiguration.getAccessTokenExpiration(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining("/"))
            );
        }
    }

    @Transactional
    public OauthInfo refresh(String refreshTokenString) {
        var claims = this.getClaimsFromEncryptedToken(refreshTokenString);
        try (
            var _ = MDC.putCloseable("jti", claims.getPayload().getId());
            var _ = MDC.putCloseable("sub", claims.getPayload().getSubject())
        ) {
            log.info("Refreshing token...");
            var refreshToken = tokenRepository.getReferenceById(UUID.fromString(claims.getPayload().getId()));
            if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
                throw new InvalidCredentialsException();
            }

            refreshToken.setExpiresAt(Instant.now());
            tokenRepository.save(refreshToken);

            var accessToken = this.persistAccessToken(refreshToken.getUser());
            refreshToken = this.persistRefreshToken(accessToken);

            return new OauthInfo(
                "Bearer",
                this.generateAccessToken(accessToken),
                this.generateRefreshToken(refreshToken),
                this.jwtConfiguration.getAccessTokenExpiration(),
                refreshToken.getUser().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining("/"))
            );
        }
    }

    private String generateAccessToken(Token accessToken) {
        return Jwts.builder()
            .issuer(this.jwtConfiguration.getIssuer())
            .audience().add(this.jwtConfiguration.getIssuer()).and()
            .id(accessToken.getId().toString())
            .subject(accessToken.getUser().getUsername())
            .issuedAt(Date.from(this.getCreationDate()))
            .expiration(Date.from(this.getAccessExpirationDate()))
            .signWith(this.jwtConfiguration.getPrivateKey())
            .compact();
    }

    private String generateRefreshToken(Token refreshToken) {
        return Jwts.builder()
            .issuer(this.jwtConfiguration.getIssuer())
            .audience().add(this.jwtConfiguration.getIssuer()).and()
            .id(refreshToken.getId().toString())
            .subject(refreshToken.getAccessToken().getId().toString())
            .issuedAt(Date.from(this.getCreationDate()))
            .expiration(Date.from(this.getRefreshExpirationDate()))
            .encryptWith(this.jwtConfiguration.getPublicKey(), Jwts.KEY.ECDH_ES, Jwts.ENC.A256GCM)
            .compact();
    }

    private Token persistAccessToken(User user) {
        var token = new Token();
        token.setType(TokenType.ACCESS);
        token.setUser(user);
        token.setExpiresAt(this.getAccessExpirationDate());

        return tokenRepository.save(token);
    }

    private Token persistRefreshToken(Token accessToken) {
        var token = new Token();
        token.setType(TokenType.REFRESH);
        token.setUser(accessToken.getUser());
        token.setAccessToken(accessToken);
        token.setExpiresAt(this.getRefreshExpirationDate());

        return tokenRepository.save(token);
    }

    private Instant getCreationDate() {
        return Instant.now();
    }

    private Instant getAccessExpirationDate() {
        return Instant.now().plusSeconds(this.jwtConfiguration.getAccessTokenExpiration());
    }

    private Instant getRefreshExpirationDate() {
        return Instant.now().plusSeconds(this.jwtConfiguration.getRefreshTokenExpiration());
    }

    public Jws<Claims> getClaimsFromSignedToken(String token) {
        try {
            return Jwts
                .parser()
                .verifyWith(this.jwtConfiguration.getPublicKey())
                .requireAudience(this.jwtConfiguration.getIssuer())
                .build()
                .parseSignedClaims(token);
        } catch (MissingClaimException | IncorrectClaimException | ExpiredJwtException | MalformedJwtException e) {
            log.error("Could not parse token", e);
            return null;
        }
    }

    public Jwe<Claims> getClaimsFromEncryptedToken(String token) {
        try {
            return Jwts
                .parser()
                .decryptWith(this.jwtConfiguration.getPrivateKey())
                .requireAudience(this.jwtConfiguration.getIssuer())
                .build()
                .parseEncryptedClaims(token);
        } catch (MissingClaimException | IncorrectClaimException | ExpiredJwtException | MalformedJwtException e) {
            log.error("Could not parse token", e);
            return null;
        }
    }
}
