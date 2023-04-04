package br.com.demo.domains.users.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.demo.domains.users.UserEntity;
import br.com.demo.domains.users.UserRepository;
import br.com.demo.domains.users.UserService;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<UserEntity> getAll() {
    return this.userRepository.findAll();
  }

  @Override
  public UserEntity getById(UUID id) {
    return this.userRepository.findById(id).get();
  }

  @Override
  public UserEntity getByUsername(String username) {
    return this.userRepository.findByUsername(username).orElse(null);
  }

}
