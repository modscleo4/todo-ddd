package br.dev.modscleo4.todo.application.auth;

import br.dev.modscleo4.todo.TestUtils;
import br.dev.modscleo4.todo.domain.auth.JwtConfiguration;
import br.dev.modscleo4.todo.domain.user.User;
import br.dev.modscleo4.todo.infrastructure.persistence.JpaTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class JwtTokenServiceTest {
    @Mock
    private JwtConfiguration jwtConfiguration;
    @Mock
    private JpaTokenRepository tokenRepository;
    @InjectMocks
    private JwtTokenServiceAdapter jwtTokenService;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        var keyGen = KeyPairGenerator.getInstance("EC");
        var spec = new ECGenParameterSpec("secp256r1");
        keyGen.initialize(spec, new SecureRandom());
        var keyPair = keyGen.generateKeyPair();

        lenient().when(jwtConfiguration.getIssuer()).thenReturn("__issuer__");
        lenient().when(jwtConfiguration.getAccessTokenExpiration()).thenReturn(600L);
        lenient().when(jwtConfiguration.getRefreshTokenExpiration()).thenReturn(1200L);
        lenient().when(jwtConfiguration.getPublicKey()).thenReturn((ECPublicKey) keyPair.getPublic());
        lenient().when(jwtConfiguration.getPrivateKey()).thenReturn((ECPrivateKey) keyPair.getPrivate());
    }

    @Test
    void authenticate_shouldPersistTokensAndReturnOauthInfo() {
        var user = new User();
        user.setId(java.util.UUID.randomUUID());
        user.setEmail("a@b.com");

        TestUtils.mockSave(tokenRepository);

        var oauth = jwtTokenService.authenticate(user);

        assertNotNull(oauth);
        assertNotNull(oauth.getAccessToken());
        assertNotNull(oauth.getRefreshToken());
        assertEquals(600L, oauth.getExpiresIn());
    }

    @Test
    void getClaimsFromSignedToken_shouldReturnNullOnInvalid() {
        var claims = jwtTokenService.getClaimsFromSignedToken("invalid");
        assertNull(claims);
    }
}

