package br.com.demo.exception;

import br.com.demo.exception.codes.ErrorCode;

public class RevokedTokenException extends UnauthorizedException {
  public RevokedTokenException() {
    super(ErrorCode.TOKEN_REVOKED.getCode());
  }
}
