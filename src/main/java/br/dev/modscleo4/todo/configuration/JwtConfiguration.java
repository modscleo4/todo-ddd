package br.dev.modscleo4.todo.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
@Getter
public class JwtConfiguration {
    @Value("${security.jwt.issuer}:${spring.application.name}")
    private String issuer;

    private final ECPublicKey publicKey;
    private final ECPrivateKey privateKey;

    @Value("${security.jwt.expiration.accessToken}")
    private long accessTokenExpiration;

    @Value("${security.jwt.expiration.refreshToken}")
    private long refreshTokenExpiration;

    @Configuration
    static class JwtKeysConfiguration {
        @Bean
        public ECPublicKey publicKeyFromBase64(
            @Value("${security.jwt.publicKey}") String base64Encoded
        ) throws NoSuchAlgorithmException, InvalidKeySpecException {
            var bytes = Base64.getMimeDecoder().decode(base64Encoded);
            var spec = new X509EncodedKeySpec(bytes);
            return (ECPublicKey) KeyFactory.getInstance("EC").generatePublic(spec);
        }

        @Bean
        public ECPrivateKey privateKeyFromBase64(
            @Value("${security.jwt.privateKey}") String base64Encoded
        ) throws NoSuchAlgorithmException, InvalidKeySpecException {
            var bytes = Base64.getMimeDecoder().decode(base64Encoded);
            var spec = new PKCS8EncodedKeySpec(bytes);
            return (ECPrivateKey) KeyFactory.getInstance("EC").generatePrivate(spec);
        }
    }
}
