package br.com.demo.services;

import org.junit.jupiter.api.Test;

import br.com.demo.DemoApplicationTests;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTests extends DemoApplicationTests {

  @Test
  public void shouldGetAllUsers() throws Exception {
    var person1Dto = this.createMockCreatePersonDto("Usuário 1", "12345678900",
        "user1", "123456");
    var person2Dto = this.createMockCreatePersonDto("Usuário 2", "98765432100",
        "user2", "654321");

    this.personService.create(person1Dto);
    this.personService.create(person2Dto);

    var users = this.userService.getAll();
    assertThat(users.size()).isEqualTo(2);

  }

  @Test
  public void shouldGetUserById() throws Exception {
    var person1Dto = this.createMockCreatePersonDto("Usuário 1", "12345678900",
        "user1", "123456");
    var person2Dto = this.createMockCreatePersonDto("Usuário 2", "98765432100",
        "user2", "654321");

    this.personService.create(person1Dto);
    this.personService.create(person2Dto);

    var persons = this.personService.getAll();

    var storedPerson1 = persons.get(0);
    var storedPerson2 = persons.get(1);

    var storedUser1 = this.userService.getById(storedPerson1.getUser().getId());
    var storedUser2 = this.userService.getById(storedPerson2.getUser().getId());

    assertThat(storedUser1.getUsername())
        .isEqualTo(storedPerson1.getUser().getUsername());
    assertThat(storedUser1.getPassword())
        .isEqualTo(storedPerson1.getUser().getPassword());

    assertThat(storedUser2.getUsername())
        .isEqualTo(storedPerson2.getUser().getUsername());
    assertThat(storedUser2.getPassword())
        .isEqualTo(storedPerson2.getUser().getPassword());

  }

}
