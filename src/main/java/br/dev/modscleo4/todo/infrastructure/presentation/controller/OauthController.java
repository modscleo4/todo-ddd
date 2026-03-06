package br.dev.modscleo4.todo.infrastructure.presentation.controller;

import br.dev.modscleo4.todo.domain.auth.InvalidCredentialsException;
import br.dev.modscleo4.todo.domain.auth.JwtTokenServicePort;
import br.dev.modscleo4.todo.domain.user.UserServicePort;
import br.dev.modscleo4.todo.infrastructure.presentation.dto.AuthInfoDTO;
import br.dev.modscleo4.todo.infrastructure.presentation.dto.GenerateTokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@Tag(name = "Oauth2", description = "Autenticação e geração de token JWT Oauth2.")
@RequiredArgsConstructor
public class OauthController {
    private final JwtTokenServicePort jwtTokenService;
    private final UserServicePort userService;

    @PostMapping(value = "/token", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @Operation(summary = "Gera um token JWT para o usuário.")
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
