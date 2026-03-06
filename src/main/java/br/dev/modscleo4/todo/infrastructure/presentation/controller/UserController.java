package br.dev.modscleo4.todo.infrastructure.presentation.controller;

import br.dev.modscleo4.todo.domain.user.UserServicePort;
import br.dev.modscleo4.todo.infrastructure.presentation.dto.UserInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@Secured("ROLE_ADMIN")
@SecurityRequirement(name = "oauth2")
@Tag(name = "Usuários", description = "Gerenciamento de usuários pelo Admin.")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserServicePort service;

    @GetMapping("/")
    @Operation(summary = "Retorna todos os usuários.")
    public Page<UserInfoDTO> getAll(@ParameterObject Pageable pageable) {
        return service.getAll(pageable).map(UserInfoDTO::new);
    }
}
