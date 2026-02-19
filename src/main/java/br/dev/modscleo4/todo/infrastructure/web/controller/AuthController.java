package br.dev.modscleo4.todo.infrastructure.web.controller;

import br.dev.modscleo4.todo.application.token.JwtTokenService;
import br.dev.modscleo4.todo.application.user.UserService;
import br.dev.modscleo4.todo.domain.user.User;
import br.dev.modscleo4.todo.infrastructure.web.dto.AuthInfoDTO;
import br.dev.modscleo4.todo.infrastructure.web.dto.CreateUserDTO;
import br.dev.modscleo4.todo.infrastructure.web.dto.UserInfoDTO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Autenticação e gerenciamento de usuários.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    /**
     * Cria um novo usuário e retorna um token de autenticação.
     */
    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthInfoDTO> signUp(@RequestBody CreateUserDTO data) {
        var user = this.userService.create(data.email(), data.password());
        return new ResponseEntity<>(new AuthInfoDTO(this.jwtTokenService.authenticate(user)), HttpStatus.CREATED);
    }

    /**
     * Retorna as informações do usuário autenticado.
     */
    @GetMapping(value = "/user")
    public ResponseEntity<UserInfoDTO> getUser(@Parameter(hidden = true) Authentication authentication) {
        return new ResponseEntity<>(new UserInfoDTO((User) authentication.getPrincipal()), HttpStatus.OK);
    }
}
