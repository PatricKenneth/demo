package br.com.demo.security;

import org.springframework.stereotype.Service;

import br.com.demo.security.requests.LoginRequest;
import br.com.demo.security.responses.AuthResponse;

@Service
public class AuthService {

  private final SecurityService securityService;

  public AuthService(SecurityService securityService) {
    this.securityService = securityService;
  }

  public AuthResponse login(LoginRequest loginRequest) {
    TokenPair tokenPair = this.securityService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    return new AuthResponse(this.securityService.getAuthenticatedUser(), tokenPair);
  }

  public void logout() {
    this.securityService.deauthenticate();
  }

}
