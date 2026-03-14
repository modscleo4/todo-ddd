package br.dev.modscleo4.todo.infrastructure.presentation.controller;

import br.dev.modscleo4.todo.domain.profile.ProfileNotFoundException;
import br.dev.modscleo4.todo.domain.profile.ProfileServicePort;
import br.dev.modscleo4.todo.domain.user.User;
import br.dev.modscleo4.todo.infrastructure.presentation.dto.CreateProfileDTO;
import br.dev.modscleo4.todo.infrastructure.presentation.dto.PatchProfileDTO;
import br.dev.modscleo4.todo.infrastructure.presentation.dto.ProfileInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/profile")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "oauth2")
@Tag(name = "Perfil", description = "Gerenciamento do perfil do usuário.")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private final ProfileServicePort profileService;

    @GetMapping("")
    @Operation(summary = "Obtém o perfil do usuário.")
    public ProfileInfoDTO get(@Parameter(hidden = true) Authentication authentication) {
        var profile = ((User) authentication.getPrincipal()).getProfile();
        if (profile == null) {
            throw new ProfileNotFoundException();
        }

        return new ProfileInfoDTO(profile);
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Cria o perfil do usuário.")
    public ProfileInfoDTO create(
        @RequestBody CreateProfileDTO data,
        @Parameter(hidden = true) Authentication authentication
    ) {
        var user = (User) authentication.getPrincipal();
        var profile = profileService.create(user, data.name(), data.cpf(), data.birthDate());

        return new ProfileInfoDTO(profile);
    }

    @PatchMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Atualiza o perfil do usuário.")
    public ProfileInfoDTO update(
        @RequestBody PatchProfileDTO data,
        @Parameter(hidden = true) Authentication authentication
    ) {
        var user = (User) authentication.getPrincipal();
        if (user.getProfile() == null) {
            throw new ProfileNotFoundException();
        }

        var profile = profileService.update(user.getProfile(), data.name());

        return new ProfileInfoDTO(profile);
    }
}
