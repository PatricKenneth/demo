package br.com.demo.security.responses;

import java.util.Date;
import java.util.UUID;

import br.com.demo.domains.users.UserEntity;
import br.com.demo.security.TokenPair;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponse {
  private UUID id;
  private Date createdAt;
  private Date updatedAt;
  private String name;
  private String username;

  private TokenPair tokenPair;

  public AuthResponse(UserEntity user, TokenPair tokenPair) {
    this.id = user.getId();
    this.createdAt = user.getCreatedAt();
    this.updatedAt = user.getUpdatedAt();
    this.name = null;
    this.username = user.getUsername();
    this.tokenPair = tokenPair;
  }

}
