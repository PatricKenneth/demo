package br.com.demo.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.demo.domains.users.UserEntity;
import br.com.demo.domains.users.UserService;
import br.com.demo.exception.ExpiredTokenException;
import br.com.demo.exception.RevokedTokenException;
import br.com.demo.exception.UnauthorizedException;
import br.com.demo.exception.UserInvalidCredentialsException;
import br.com.demo.helpers.TimeBuilder;
import br.com.demo.infrastructure.config.Properties;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SecurityService {
  private final String TOKEN_HASH_CLAIM = "_hash";
  private final String SECRET_KEY = "1q2w3e4r";

  private final Properties properties;
  private final UserService userService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public SecurityService(Properties properties, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.properties = properties;
    this.userService = userService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public UserDetails getUserDetailsFromAccessToken(String token) {
    UserEntity user = this.getUserFromAccessToken(token);
    return new CustomUserDetails(user);
  }

  public TokenPair authenticate(String username, String password) {
    UserEntity user = this.userService.getByUsername(username);

    if (user == null || !this.bCryptPasswordEncoder.matches(password, user.getPassword())) {
      throw new UserInvalidCredentialsException();
    }

    UserDetails details = new CustomUserDetails(user);
    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(details, null));

    return this.createTokenPair(user);
  }

  public void deauthenticate() {
    SecurityContextHolder.clearContext();
  }

  public UserEntity getAuthenticatedUser() {
    var context = SecurityContextHolder.getContext();
    if (context.getAuthentication() == null) {
      return null;
    }
    Object principal = context.getAuthentication().getPrincipal();
    if (!(principal instanceof CustomUserDetails)) {
      return null;
    }
    return ((CustomUserDetails) principal).getUser();
  }

  public String refreshAccessToken(String refreshToken) {
    return this.createAccessToken(this.getUserFromRefreshToken(refreshToken));
  }

  private UserEntity getUserFromRefreshToken(String token) {
    return this.getUserFromJwtToken(token, this.SECRET_KEY);
  }

  private UserEntity getUserFromAccessToken(String token) {
    return this.getUserFromJwtToken(token, this.SECRET_KEY);
  }

  private UserEntity getUserFromJwtToken(String token, String secret) {
    try {
      var claims = Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
          .parseClaimsJws(token);

      String subject = claims.getBody().getSubject();
      String tokenHash = claims.getBody().get(TOKEN_HASH_CLAIM, String.class);
      UserEntity user = this.userService.getByUsername(subject);
      if (!Objects.equals(user.getTokenHash(), tokenHash)) {
        throw new RevokedTokenException();
      }
      return user;
    } catch (JwtException ex) {
      if (ex instanceof ExpiredJwtException) {
        throw new ExpiredTokenException();
      }
      throw new UnauthorizedException();
    }
  }

  private TokenPair createTokenPair(UserEntity user) {
    return new TokenPair(this.createRefreshToken(user), this.createAccessToken(user));
  }

  private String createAccessToken(UserEntity user) {
    return this.createToken(user, this.properties.getAccessToken());
  }

  private String createRefreshToken(UserEntity user) {
    return this.createToken(user, this.properties.getRefreshToken());
  }

  private String createToken(UserEntity user, Properties.JwtConfig jwtConfig) {
    LocalDateTime expire = TimeBuilder.getDateTime().plusSeconds(jwtConfig.getDuration());
    Instant instant = TimeBuilder.getDateTime().atZone(TimeBuilder.DEFAULT_ZONE_ID).toInstant();
    var builder = Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuedAt(Date.from(instant))
        .claim(this.TOKEN_HASH_CLAIM, user.getTokenHash())
        .setExpiration(Date.from(expire.atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(SignatureAlgorithm.HS512,
            this.SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    return builder.compact();
  }

}
