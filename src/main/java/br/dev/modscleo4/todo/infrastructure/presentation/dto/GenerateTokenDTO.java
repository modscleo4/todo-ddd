package br.dev.modscleo4.todo.infrastructure.presentation.dto;

import br.dev.modscleo4.todo.domain.auth.GrantType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.BindParam;

public record GenerateTokenDTO(
    @BindParam("grant_type") @JsonProperty("grant_type") GrantType grantType,
    String username,
    String password,
    @BindParam("client_id") @JsonProperty("client_id") String clientId,
    @BindParam("client_secret") @JsonProperty("client_secret") String clientSecret,
    @BindParam("refresh_token") @JsonProperty("refresh_token") String refreshToken,
    String scope
) {

}
