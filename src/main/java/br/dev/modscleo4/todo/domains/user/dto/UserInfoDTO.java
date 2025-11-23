package br.dev.modscleo4.todo.domains.user.dto;

import br.dev.modscleo4.todo.domains.user.User;
import br.dev.modscleo4.todo.domains.user.UserRole;

import java.util.UUID;

public record UserInfoDTO(
    UUID id,
    String email,
    UserRole role,
    ProfileInfoDTO profile
) {
    public UserInfoDTO(User user) {
        this(
            user.getId(),
            user.getEmail(),
            user.getRole(),
            user.getProfile() != null ? new ProfileInfoDTO(user.getProfile()) : null
        );
    }
}
