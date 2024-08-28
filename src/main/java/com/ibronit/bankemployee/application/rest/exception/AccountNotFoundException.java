package com.ibronit.bankemployee.application.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Account not found by the provided accoundId")
public class AccountNotFoundException extends RuntimeException {}
