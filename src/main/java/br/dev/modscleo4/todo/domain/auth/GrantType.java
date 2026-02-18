package br.dev.modscleo4.todo.domain.auth;


import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.EnumeratedValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GrantType {
    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token");

    @EnumeratedValue
    @JsonValue
    private final String value;
}
