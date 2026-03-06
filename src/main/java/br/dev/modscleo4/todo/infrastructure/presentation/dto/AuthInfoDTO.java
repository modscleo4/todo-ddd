package br.dev.modscleo4.todo.infrastructure.presentation.dto;

import br.dev.modscleo4.todo.domain.auth.OauthInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthInfoDTO(
    @JsonProperty("token_type") String tokenType,
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("refresh_token") String refreshToken,
    @JsonProperty("expires_in")
    Long expiresIn,
    String scope
) {
    public AuthInfoDTO(OauthInfo oauthInfo) {
        this(
            oauthInfo.getTokenType(),
            oauthInfo.getAccessToken(),
            oauthInfo.getRefreshToken(),
            oauthInfo.getExpiresIn(),
            oauthInfo.getScope()
        );
    }
}
