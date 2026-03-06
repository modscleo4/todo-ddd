package br.dev.modscleo4.todo.infrastructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Todo DDD", version = "v1"))
@SecurityScheme(
    name = "oauth2",
    type = SecuritySchemeType.OAUTH2,
    flows = @OAuthFlows(
        password = @OAuthFlow(
            tokenUrl = "/oauth/token",
            scopes = {
                @OAuthScope(name = "note.read", description = "Note: Read access"),
                @OAuthScope(name = "note.write", description = "Note: Write access"),
                @OAuthScope(name = "profile.read", description = "Profile: Read access"),
                @OAuthScope(name = "profile.write", description = "Profile: Write access"),
            }
        )
    )
)
public class OpenApiConfig {

}
