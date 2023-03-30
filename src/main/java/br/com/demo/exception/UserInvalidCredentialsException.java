package br.com.demo.exception;

import br.com.demo.exception.codes.ErrorCode;

public class UserInvalidCredentialsException extends UnauthorizedException {
  public UserInvalidCredentialsException() {
    super(ErrorCode.USER_INVALID_CREDENTIALS.getCode());
  }
}
