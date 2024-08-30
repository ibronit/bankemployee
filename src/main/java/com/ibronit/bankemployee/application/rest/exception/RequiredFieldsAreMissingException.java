package com.ibronit.bankemployee.application.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "the balance field is required")
public class RequiredFieldsAreMissingException extends RuntimeException {}
