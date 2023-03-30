package br.com.demo.domains.person;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.demo.domains.person.dto.CreatePersonDto;
import br.com.demo.helpers.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController(value = "Person Controller")
@RequestMapping(value = "/api/persons/")
@Slf4j
public class PersonController {

  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Cria um Novo Cadastro na Plataforma")
  public ResponseEntity<Object> create(@RequestBody CreatePersonDto dto) {
    try {
      log.info("Criando Novo Cadastro -> {}", dto.getName());
      UUID storedPerson = this.personService.create(dto);
      log.info("Cadastro Finalizado -> {}", dto.getName());
      return ResponseBuilder.CREATED(storedPerson);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseBuilder.INTERNAL_SERVER_ERROR(e.getMessage());
    }
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Recupera Cadastro por Id")
  public ResponseEntity<Object> getById(@PathVariable(name = "id") UUID id) {
    try {
      log.info("Buscando Cadastro por ID -> {}", id);
      PersonEntity person = this.personService.getById(id);
      log.info("Busca Finalizada -> {}", id);
      return ResponseBuilder.OK(person);
    } catch (NoSuchElementException e) {
      log.error(e.getMessage());
      return ResponseBuilder.NOT_FOUND(e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseBuilder.INTERNAL_SERVER_ERROR(e.getMessage());
    }
  }

}
