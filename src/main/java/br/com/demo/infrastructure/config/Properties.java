package br.com.demo.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "app")
@Component
@Getter
@Setter
public class Properties {

  private ApiDocs apiDocs;
  private JwtConfig accessToken;
  private JwtConfig refreshToken;

  @Getter
  @Setter
  public static class ApiDocs {
    private String username;
    private String password;
  }

  @Getter
  @Setter
  public static class JwtConfig {
    private Long duration;
  }

}
