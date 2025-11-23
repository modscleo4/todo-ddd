package br.dev.modscleo4.todo.domains.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthInfoDTO(
    @JsonProperty("token_type") String tokenType,
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("refresh_token") String refreshToken,
    @JsonProperty("expires_in")
    Long expiresIn,
    String scope
) {

}
