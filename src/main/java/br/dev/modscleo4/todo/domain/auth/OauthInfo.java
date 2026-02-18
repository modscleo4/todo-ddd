package br.dev.modscleo4.todo.domain.auth;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OauthInfo {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String scope;
}
