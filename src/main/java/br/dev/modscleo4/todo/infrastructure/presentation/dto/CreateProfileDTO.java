package br.dev.modscleo4.todo.infrastructure.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record CreateProfileDTO(
    @Valid @NotEmpty @Max(255) String name,
    @Valid @NotEmpty @Min(11) @Max(11) @CPF String cpf,
    @Valid @NotEmpty @Past LocalDate birthDate
) {

}
