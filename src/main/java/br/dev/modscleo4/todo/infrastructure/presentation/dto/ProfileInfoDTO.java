package br.dev.modscleo4.todo.infrastructure.presentation.dto;

import br.dev.modscleo4.todo.domain.profile.Profile;

import java.util.UUID;

public record ProfileInfoDTO(
    UUID id,
    String name
) {
    public ProfileInfoDTO(Profile profile) {
        this(profile.getId(), profile.getName());
    }
}
