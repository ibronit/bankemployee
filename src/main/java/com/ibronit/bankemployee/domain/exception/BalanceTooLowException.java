package com.ibronit.bankemployee.domain.exception;

public class BalanceTooLowException extends RuntimeException {

  public BalanceTooLowException(String message) {
    super(message);
  }
}
