package br.dev.modscleo4.todo.infrastructure.web.dto;

import br.dev.modscleo4.todo.domain.auth.GrantType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GenerateTokenDTO(
    @JsonProperty("grant_type") GrantType grantType,
    String username,
    String password,
    @JsonProperty("client_id") String clientId,
    @JsonProperty("client_secret") String clientSecret
) {

}
