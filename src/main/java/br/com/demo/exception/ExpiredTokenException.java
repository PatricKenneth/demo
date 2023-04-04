package br.com.demo.exception;

import br.com.demo.exception.codes.ErrorCode;

public class ExpiredTokenException extends UnauthorizedException {
  public ExpiredTokenException() {
    super(ErrorCode.TOKEN_EXPIRED.getCode());
  }
}
