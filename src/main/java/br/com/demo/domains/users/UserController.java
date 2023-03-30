package br.com.demo.domains.users;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.demo.helpers.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController(value = "User Controller")
@RequestMapping(value = "/api/users/")
@Slf4j
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Lista de Todos os Usuários")
  public ResponseEntity<Object> getAll() {
    try {
      List<UserEntity> users = this.userService.getAll();
      return ResponseBuilder.OK(users);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseBuilder.INTERNAL_SERVER_ERROR(e.getMessage());
    }
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Lista Usuário por Id")
  public ResponseEntity<Object> getById(@PathVariable(name = "id") UUID id) {
    try {
      UserEntity user = this.userService.getById(id);
      return ResponseBuilder.OK(user);
    } catch (NoSuchElementException e) {
      log.error(e.getMessage());
      return ResponseBuilder.NOT_FOUND(e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseBuilder.INTERNAL_SERVER_ERROR(e.getMessage());
    }
  }

}
