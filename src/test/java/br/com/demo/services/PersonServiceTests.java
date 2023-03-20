package br.com.demo.services;

import org.junit.jupiter.api.Test;

import br.com.demo.DemoApplicationTests;
import br.com.demo.helpers.EncryptPassword;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonServiceTests extends DemoApplicationTests {

  @Test
  public void shouldCreatePerson() throws Exception {
    final String username = "pkenneth";
    final String password = "123456";

    var personDto = this.createMockCreatePersonDto("Patric Kenneth", "12345678900", username, password);

    var personId = this.personService.create(personDto);
    var storedPerson = this.personRepository.findById(personId).get();

    assertThat(storedPerson.getName()).isEqualTo(personDto.getName());
    assertThat(storedPerson.getDocumentNumber()).isEqualTo(personDto.getDocumentNumber());

    assertThat(storedPerson.getUser().getUsername()).isEqualTo(username);
    assertThat(storedPerson.getUser().getPassword()).isEqualTo(EncryptPassword.encrypt(password.concat(username)));

  }

  @Test
  public void shouldPersonById() throws Exception {
    var personDto = this.createMockCreatePersonDto("Patric Kenneth", "12345678900", "pkenneth", "123456");

    var personId = this.personService.create(personDto);
    var storedPerson = this.personRepository.findById(personId).get();

    assertThat(personId).isEqualTo(storedPerson.getId());

  }

  @Test
  public void shouldAllPersons() throws Exception {
    var personDto = this.createMockCreatePersonDto("Patric Kenneth", "12345678900", "pkenneth", "123456");
    this.personService.create(personDto);

    var persons = this.personService.getAll();

    assertThat(persons.size()).isEqualTo(1);

  }

}
