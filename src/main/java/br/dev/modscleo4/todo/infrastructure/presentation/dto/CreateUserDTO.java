package br.dev.modscleo4.todo.infrastructure.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
    @Valid @Email @Size(max = 255) @NotEmpty String email,
    @Valid @NotEmpty @Size(min = 8, max = 64) String password
) {

}
