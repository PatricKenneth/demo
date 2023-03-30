package br.com.demo.exception;

import org.springframework.http.HttpStatus;

import br.com.demo.exception.codes.ErrorCode;
import br.com.demo.exception.responses.GenericErrorResponse;

public class UnauthorizedException extends ApiError {

  public UnauthorizedException() {
    super(HttpStatus.UNAUTHORIZED, new GenericErrorResponse(ErrorCode.ERROR_UNAUTHORIZED.getCode()));
  }

  protected UnauthorizedException(String code) {
    super(HttpStatus.UNAUTHORIZED, new GenericErrorResponse(code));
  }

}
