package br.com.demo.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.util.UrlPathHelper;

import br.com.demo.exception.UnauthorizedException;

public class TokenFilter extends OncePerRequestFilter {

  private final List<String> nonFilteredPaths = List.of("/api/auth/login");

  private final HandlerExceptionResolver handlerExceptionResolver;
  private final SecurityService securityService;

  public TokenFilter(HandlerExceptionResolver handlerExceptionResolver, SecurityService securityService) {
    this.handlerExceptionResolver = handlerExceptionResolver;
    this.securityService = securityService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String tokenJwt = request.getHeader("Authorization");
    if (tokenJwt != null) {
      tokenJwt = tokenJwt.replace("Bearer ", "").strip();

      try {
        UserDetails userDetails = this.securityService.getUserDetailsFromAccessToken(tokenJwt);
        if (userDetails != null) {
          Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
              userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (Exception ex) {
        this.handlerExceptionResolver.resolveException(request, response, null, ex);
        return;
      }

      filterChain.doFilter(request, response);
    } else {
      this.handlerExceptionResolver.resolveException(request, response, null, new UnauthorizedException());
      return;
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    final String path = new UrlPathHelper().getPathWithinApplication(request);
    return nonFilteredPaths.stream().anyMatch(path::startsWith);
  }

}
