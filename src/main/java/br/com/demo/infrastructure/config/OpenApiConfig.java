package br.com.demo.infrastructure.config;

import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  private final String securitySchemeName = "bearerAuth";

  private Info getInfo() {
    final String title = "API Demo";
    final String version = "1.0";
    final String description = "API Demo - 1.0";

    return new Info().title(title).version(version).description(description);
  }

  private SecurityScheme getSecurityScheme() {
    return new SecurityScheme()
        .name(securitySchemeName)
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT");
  }

  private Components getComponents() {
    return new Components().addSecuritySchemes(this.securitySchemeName, this.getSecurityScheme());
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(this.getComponents())
        .info(this.getInfo());
  }

}
