package br.dev.modscleo4.todo.infrastructure.web.controller;

import br.dev.modscleo4.todo.domain.auth.JwtTokenServicePort;
import br.dev.modscleo4.todo.domain.user.User;
import br.dev.modscleo4.todo.domain.user.UserServicePort;
import br.dev.modscleo4.todo.infrastructure.web.dto.AuthInfoDTO;
import br.dev.modscleo4.todo.infrastructure.web.dto.CreateUserDTO;
import br.dev.modscleo4.todo.infrastructure.web.dto.UserInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Autenticação e gerenciamento de usuários.")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JwtTokenServicePort jwtTokenService;
    private final UserServicePort userService;

    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria um novo usuário e retorna um token de autenticação.")
    public ResponseEntity<AuthInfoDTO> signUp(@RequestBody CreateUserDTO data) {
        var user = this.userService.create(data.email(), data.password());
        return new ResponseEntity<>(new AuthInfoDTO(this.jwtTokenService.authenticate(user)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/user")
    @Operation(summary = "Retorna as informações do usuário autenticado.")
    @SecurityRequirement(name = "oauth2")
    public ResponseEntity<UserInfoDTO> getUser(@Parameter(hidden = true) Authentication authentication) {
        return new ResponseEntity<>(new UserInfoDTO((User) authentication.getPrincipal()), HttpStatus.OK);
    }
}
