package tech.code.codetech.api.configuration.security.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Letícia Lombardi"
        ),
        security = @SecurityRequirement(name = "Bearer")
)
@SecurityScheme(
        name = "Login",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Utilize um token válido para autenticação. Exemplo: 'AHJsjksAYFARkplKKEasiO' "
)
public class OpenApiConfig {
}
