package br.com.demo.security.enums;

import lombok.Getter;

import java.util.Arrays;

public enum UserRole {
  ROLE_ADMIN("administrador");

  @Getter
  private final String key;

  private UserRole(String key) {
    this.key = key;
  }

  public static UserRole getByProfileKey(String key) {
    return Arrays.asList(UserRole.values()).stream().filter(r -> r.key.equals(key))
        .findAny().orElse(null);
  }

}
