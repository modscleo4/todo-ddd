package br.dev.modscleo4.todo.domains.auth;

import br.dev.modscleo4.todo.domains.auth.dto.AuthInfoDTO;
import br.dev.modscleo4.todo.domains.auth.dto.GenerateTokenDTO;
import br.dev.modscleo4.todo.domains.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AuthInfoDTO> signIn(@RequestBody GenerateTokenDTO data) {
        var user = this.userService.authenticate(data.username(), data.password());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this.jwtTokenService.authenticate(user.get()));
    }
}
