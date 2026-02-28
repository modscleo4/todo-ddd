package br.dev.modscleo4.todo.infrastructure.web.controller;

import br.dev.modscleo4.todo.domain.auth.InvalidCredentialsException;
import br.dev.modscleo4.todo.domain.auth.JwtTokenServicePort;
import br.dev.modscleo4.todo.domain.user.UserServicePort;
import br.dev.modscleo4.todo.infrastructure.web.dto.AuthInfoDTO;
import br.dev.modscleo4.todo.infrastructure.web.dto.GenerateTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Autenticação e geração de token JWT Oauth2.
 */
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {
    private final JwtTokenServicePort jwtTokenService;
    private final UserServicePort userService;

    /**
     * Gera um token JWT para o usuário.
     */
    @PostMapping(value = "/token", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public AuthInfoDTO signIn(@RequestBody GenerateTokenDTO data) {
        var info = switch (data.grantType()) {
            case PASSWORD -> this.jwtTokenService.authenticate(
                this.userService.authenticate(data.username(), data.password())
                    .orElseThrow(InvalidCredentialsException::new)
            );

            case REFRESH_TOKEN -> this.jwtTokenService.refresh(data.refreshToken());

            default -> throw new IllegalArgumentException("Invalid grant type.");
        };

        return new AuthInfoDTO(info);
    }
}
