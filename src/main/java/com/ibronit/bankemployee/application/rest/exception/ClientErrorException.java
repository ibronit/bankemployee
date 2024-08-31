package com.ibronit.bankemployee.application.rest.exception;

public class ClientErrorException extends RuntimeException {

  public ClientErrorException(String message) {
    super(message);
  }
}
