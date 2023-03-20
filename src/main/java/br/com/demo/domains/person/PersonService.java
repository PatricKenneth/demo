package br.com.demo.domains.person;

import java.util.List;
import java.util.UUID;

import br.com.demo.domains.person.dto.CreatePersonDto;

public interface PersonService {

  UUID create(CreatePersonDto dto) throws Exception;

  PersonEntity getById(UUID id) throws Exception;

  List<PersonEntity> getAll() throws Exception;

}
