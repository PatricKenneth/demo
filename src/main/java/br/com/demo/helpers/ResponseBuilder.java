package br.com.demo.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

  public static ResponseEntity<Object> INTERNAL_SERVER_ERROR(Object body) {
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public static ResponseEntity<Object> OK(Object body) {
    return new ResponseEntity<>(body, HttpStatus.OK);
  }

  public static ResponseEntity<Object> CREATED(Object body) {
    return new ResponseEntity<>(body, HttpStatus.CREATED);
  }

  public static ResponseEntity<Object> NOT_FOUND(Object body) {
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

}
