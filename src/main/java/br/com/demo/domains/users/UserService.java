package br.com.demo.domains.users;

import java.util.List;
import java.util.UUID;

public interface UserService {

  List<UserEntity> getAll();

  UserEntity getById(UUID id);

  UserEntity getByUsername(String username);

}
