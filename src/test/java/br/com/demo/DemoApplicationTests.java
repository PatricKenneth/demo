package br.com.demo;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.demo.domains.person.PersonEntity;
import br.com.demo.domains.person.PersonRepository;
import br.com.demo.domains.person.PersonService;
import br.com.demo.domains.person.dto.CreatePersonDto;
import br.com.demo.domains.users.UserEntity;
import br.com.demo.domains.users.UserRepository;
import br.com.demo.domains.users.UserService;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public abstract class DemoApplicationTests {

  @Autowired
  protected MockMvc mockMvc;
  @SpyBean
  protected ObjectMapper mapper;
  @SpyBean
  protected PersonRepository personRepository;
  @SpyBean
  protected UserRepository userRepository;
  @SpyBean
  protected PersonService personService;
  @SpyBean
  protected UserService userService;

  @BeforeEach
  public void beforeEach() throws Exception {
    setup();
  }

  private void setup() throws Exception {
    this.personRepository.deleteAll();
    this.userRepository.deleteAll();
  }

  public CreatePersonDto createMockCreatePersonDto(String name, String documentNumber, String username,
      String password) {
    var dto = new CreatePersonDto();
    dto.setName(name);
    dto.setDocumentNumber(documentNumber);
    dto.setUsername(username);
    dto.setPassword(password);
    return dto;
  }

  public PersonEntity createMockPersonEntity(String name, String documentNumber, String username,
      String password) {
    var person = new PersonEntity();
    person.setName(name);
    person.setDocumentNumber(documentNumber);
    person.setUser(new UserEntity(username, password));
    return person;
  }

}
