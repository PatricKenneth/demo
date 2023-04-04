package br.com.demo.infrastructure.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import br.com.demo.security.SecurityService;
import br.com.demo.security.TokenFilter;

@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig {
  private static final String API_PATH = "/api/";
  private static final String DOCS_PATH = "/api-docs/";

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private String getPathPattern(String path) {
    return path + "**";
  }

  @Order(1)
  @Configuration
  public class DocsSecurityConfig {
    private final String USER_ROLE = "DOCS_USER";
    private final Properties properties;

    public DocsSecurityConfig(Properties properties) {
      this.properties = properties;
    }

    @Bean
    public SecurityFilterChain docsSecurityChain(HttpSecurity http) throws Exception {
      http.requestMatchers(request -> request.antMatchers(getPathPattern(DOCS_PATH)));
      http.authorizeHttpRequests(security -> security.antMatchers(DOCS_PATH + "swagger-ui/**")
          .permitAll().anyRequest().hasRole(USER_ROLE));
      http.httpBasic(withDefaults());
      http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
      return http.build();
    }

    @Bean
    public UserDetailsService inMemoryUserDetails() {
      UserDetails user = User.builder()
          .username(this.properties.getApiDocs().getUsername())
          .password(passwordEncoder().encode(this.properties.getApiDocs().getPassword()))
          .roles(USER_ROLE)
          .build();
      return new InMemoryUserDetailsManager(user);
    }

  }

  @Order(2)
  @Configuration
  public class ApiSecurityConfig {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final SecurityService securityService;

    public ApiSecurityConfig(HandlerExceptionResolver handlerExceptionResolver, SecurityService securityService) {
      this.handlerExceptionResolver = handlerExceptionResolver;
      this.securityService = securityService;
    }

    public TokenFilter getTokenFilter() {
      return new TokenFilter(this.handlerExceptionResolver, this.securityService);
    }

    @Bean
    public SecurityFilterChain apiSecurityChain(HttpSecurity http) throws Exception {
      http.cors(withDefaults());
      http.csrf(httpCsrf -> httpCsrf.disable());
      http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
      http.exceptionHandling(
          handling -> handling.authenticationEntryPoint((request, response, authException) -> response
              .sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getLocalizedMessage())));
      http.authorizeHttpRequests(authorize -> authorize.antMatchers(getPathPattern(API_PATH))
          .permitAll().anyRequest().authenticated());
      http.addFilterBefore(this.getTokenFilter(), UsernamePasswordAuthenticationFilter.class);
      return http.build();
    }

  }

}