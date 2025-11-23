package br.dev.modscleo4.todo.domains.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GenerateTokenDTO(
    @JsonProperty("grant_type") String grantType,
    String username,
    String password,
    @JsonProperty("client_id") String clientId,
    @JsonProperty("client_secret") String clientSecret
) {

}
