package br.com.demo.controllers;

import org.junit.jupiter.api.Test;

import br.com.demo.DemoApplicationTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

public class UserControllerTests extends DemoApplicationTests {
  private final String BASE_URL = "/users";

  @Test
  public void shouldGetAllUsers() throws Exception {
    // Testa o status code 200
    mockMvc.perform(get(BASE_URL))
        .andExpect(status().isOk());

  }

  @Test
  public void shouldGetUserById() throws Exception {
    final String url = BASE_URL.concat("/{id}");
    var person = this.createMockPersonEntity("Patric Kenneth", "123456789", "pkenneth", "123456");
    UUID storedUser = this.personRepository.save(person).getUser().getId();

    // Testa o status 200
    mockMvc.perform(get(url, storedUser))
        .andExpect(status().isOk());

    // Testa o status 404
    mockMvc.perform(get(url, UUID.randomUUID()))
        .andExpect(status().isNotFound());

    // Testa o status 400
    mockMvc.perform(get(url, "-1"))
        .andExpect(status().isBadRequest());

  }

}
