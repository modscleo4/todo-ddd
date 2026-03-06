package br.dev.modscleo4.todo.infrastructure.presentation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

public record CreateProfileDTO(
    @NotEmpty @Max(255) String name,
    @NotEmpty @Min(11) @Max(11) @CPF String cpf,
    @NotEmpty @Past Date birthDate
) {

}
