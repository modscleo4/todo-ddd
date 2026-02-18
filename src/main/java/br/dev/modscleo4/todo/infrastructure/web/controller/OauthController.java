package br.dev.modscleo4.todo.infrastructure.web.controller;

import br.dev.modscleo4.todo.application.token.JwtTokenService;
import br.dev.modscleo4.todo.application.user.UserService;
import br.dev.modscleo4.todo.domain.auth.InvalidCredentialsException;
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
    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    /**
     * Gera um token JWT para o usuário.
     */
    @PostMapping(value = "/token", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public AuthInfoDTO signIn(@RequestBody GenerateTokenDTO data) {
        var user = this.userService.authenticate(data.username(), data.password()).orElseThrow(InvalidCredentialsException::new);

        return new AuthInfoDTO(this.jwtTokenService.authenticate(user));
    }
}
