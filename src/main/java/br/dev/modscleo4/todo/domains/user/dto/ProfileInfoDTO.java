package br.dev.modscleo4.todo.domains.user.dto;

import br.dev.modscleo4.todo.domains.user.Profile;

import java.util.UUID;

public record ProfileInfoDTO(
    UUID id,
    String name
) {
    public ProfileInfoDTO(Profile profile) {
        this(profile.getId(), profile.getName());
    }
}
