package br.com.demo.domains.person.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.demo.domains.person.PersonEntity;
import br.com.demo.domains.person.PersonRepository;
import br.com.demo.domains.person.PersonService;
import br.com.demo.domains.person.dto.CreatePersonDto;
import br.com.demo.domains.users.UserEntity;

@Service
public class PersonServiceImpl implements PersonService {

  private final PersonRepository personRepository;
  private final PasswordEncoder passwordEncoder;

  public PersonServiceImpl(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
    this.personRepository = personRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UUID create(CreatePersonDto dto) throws Exception {
    PersonEntity newPerson = new PersonEntity();
    newPerson.setName(dto.getName());
    newPerson.setDocumentNumber(dto.getDocumentNumber());
    String encryptedPassword = this.passwordEncoder.encode(dto.getPassword());
    newPerson.setUser(new UserEntity(dto.getUsername(), encryptedPassword));
    return this.personRepository.save(newPerson).getId();
  }

  @Override
  public PersonEntity getById(UUID id) throws Exception {
    return this.personRepository.findById(id).get();
  }

  @Override
  public List<PersonEntity> getAll() throws Exception {
    return this.personRepository.findAll();
  }

}
