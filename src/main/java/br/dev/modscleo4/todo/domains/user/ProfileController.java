package br.dev.modscleo4.todo.domains.user;

import br.dev.modscleo4.todo.domains.user.dto.CreateProfileDTO;
import br.dev.modscleo4.todo.domains.user.dto.PatchProfileDTO;
import br.dev.modscleo4.todo.domains.user.dto.ProfileInfoDTO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("")
    public ProfileInfoDTO get(@Parameter(hidden = true) Authentication authentication) {
        var profile = ((User) authentication.getPrincipal()).getProfile();
        if (profile == null) {
            throw new ProfileNotFoundException();
        }

        return new ProfileInfoDTO(profile);
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ProfileInfoDTO create(
        @RequestBody CreateProfileDTO data,
        @Parameter(hidden = true) Authentication authentication
    ) {
        var user = (User) authentication.getPrincipal();
        var profile = profileService.create(user, data.name(), data.cpf(), data.birthDate());

        return new ProfileInfoDTO(profile);
    }

    @PatchMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ProfileInfoDTO update(
        @RequestBody PatchProfileDTO data,
        @Parameter(hidden = true) Authentication authentication
    ) {
        var user = (User) authentication.getPrincipal();
        var profile = profileService.update(user.getProfile(), data.name());

        return new ProfileInfoDTO(profile);
    }
}
