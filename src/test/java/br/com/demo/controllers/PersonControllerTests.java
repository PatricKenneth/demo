package br.com.demo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import br.com.demo.DemoApplicationTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

public class PersonControllerTests extends DemoApplicationTests {
  private final String BASE_URL = "/persons";

  @Test
  public void shouldCreatePerson() throws Exception {
    var personDto = this.createMockCreatePersonDto("Patric Kenneth", "1234567890", "pkenneth", "123456");

    // Testa o status code 201
    mockMvc.perform(
        post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personDto)))
        .andExpect(status().isCreated());

    // Testa o status code 500
    personDto.setDocumentNumber(null);
    mockMvc.perform(
        post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(personDto)))
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void shouldGetPersonById() throws Exception {
    final String url = BASE_URL.concat("/{id}");
    var person = this.createMockPersonEntity("Patric Kenneth", "123456789", "pkenneth", "123456");
    UUID storedPerson = this.personRepository.save(person).getId();

    // Testa o status code 200
    mockMvc.perform(get(url, storedPerson))
        .andExpect(status().isOk());

    // Testa o status code 404
    mockMvc.perform(get(url, UUID.randomUUID()))
        .andExpect(status().isNotFound());

    // Testa o status code 400
    mockMvc.perform(get(url, "-1"))
        .andExpect(status().isBadRequest());

  }

}
