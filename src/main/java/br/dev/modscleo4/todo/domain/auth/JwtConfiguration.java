package br.dev.modscleo4.todo.domain.auth;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public interface JwtConfiguration {
    public String getIssuer();

    public ECPublicKey getPublicKey();

    public ECPrivateKey getPrivateKey();

    public long getAccessTokenExpiration();

    public long getRefreshTokenExpiration();
}
