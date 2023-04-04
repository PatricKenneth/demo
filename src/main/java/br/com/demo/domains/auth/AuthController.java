package br.com.demo.domains.auth;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.demo.exception.ExpiredTokenException;
import br.com.demo.exception.RevokedTokenException;
import br.com.demo.exception.UnauthorizedException;
import br.com.demo.exception.UserInvalidCredentialsException;
import br.com.demo.helpers.ResponseBuilder;
import br.com.demo.security.AuthService;
import br.com.demo.security.SecurityService;
import br.com.demo.security.requests.LoginRequest;
import br.com.demo.security.requests.RefreshTokenRequest;
import br.com.demo.security.responses.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController(value = "Auth Controller")
@RequestMapping(value = "/api/auth/")
@Slf4j
public class AuthController {

  private final AuthService authService;
  private final SecurityService securityService;

  public AuthController(AuthService authService, SecurityService securityService) {
    this.authService = authService;
    this.securityService = securityService;
  }

  @PostMapping("/login")
  @Operation(description = "Obtém os tokens e dados básicos de um usuário.")
  public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest request) {
    try {
      return ResponseBuilder.CREATED(this.authService.login(request));
    } catch (UserInvalidCredentialsException e) {
      return ResponseBuilder.UNAUTHORIZED("E-mail ou senha inválidos.");
    } catch (Exception e) {
      log.error("Erro Interno ao Tentar Login -> {}", e.getMessage());
      return ResponseBuilder.INTERNAL_SERVER_ERROR(e.getMessage());
    }
  }

  @PostMapping("/logout")
  @Operation(description = "Fazer logout na plataforma.")
  public ResponseEntity<Object> logout() {
    try {
      this.authService.logout();
      return ResponseBuilder.OK(null);
    } catch (Exception e) {
      log.error("Erro Interno ao Tentar Logout -> {}", e.getMessage());
      return ResponseBuilder.INTERNAL_SERVER_ERROR(e.getMessage());
    }
  }

  @PostMapping("/refresh")
  @Operation(description = "Obtém um novo access token.")
  public ResponseEntity<Object> refresh(@RequestBody @Valid RefreshTokenRequest request) {
    try {
      RefreshTokenResponse response = new RefreshTokenResponse(
          this.securityService.refreshAccessToken(request.getRefreshToken()));
      return ResponseBuilder.CREATED(response);
    } catch (RevokedTokenException e) {
      return ResponseBuilder.UNAUTHORIZED("O refresh token informado foi revogado.");
    } catch (ExpiredTokenException e) {
      return ResponseBuilder.UNAUTHORIZED("O token foi expirado.");
    } catch (UnauthorizedException e) {
      return ResponseBuilder.UNAUTHORIZED("O token informálido é inválido.");
    } catch (Exception e) {
      log.error("Erro Interno ao Tentar Login -> {}", e.getMessage());
      return ResponseBuilder.INTERNAL_SERVER_ERROR(e.getMessage());
    }
  }

}
