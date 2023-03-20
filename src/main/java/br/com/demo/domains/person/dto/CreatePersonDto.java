package br.com.demo.domains.person.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePersonDto {

  private String name;
  private String documentNumber;
  private String username;
  private String password;

}
