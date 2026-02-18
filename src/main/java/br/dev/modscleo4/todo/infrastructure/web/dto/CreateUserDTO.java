package br.dev.modscleo4.todo.infrastructure.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
    @Email @Size(max = 255) String email,
    @NotEmpty @Size(min = 8, max = 64) String password
) {

}
