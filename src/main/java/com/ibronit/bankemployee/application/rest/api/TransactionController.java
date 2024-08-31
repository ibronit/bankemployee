package com.ibronit.bankemployee.application.rest.api;

import com.ibronit.bankemployee.application.rest.exception.NotFoundException;
import com.ibronit.bankemployee.application.rest.exception.ClientErrorException;
import com.ibronit.bankemployee.application.rest.model.TransactionRequest;
import com.ibronit.bankemployee.application.rest.model.TransactionResponse;
import com.ibronit.bankemployee.domain.exception.AccountNotFoundException;
import com.ibronit.bankemployee.domain.exception.BalanceTooLowException;
import com.ibronit.bankemployee.domain.model.TransactionEntity;
import com.ibronit.bankemployee.domain.service.TransactionService;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionController implements TransasctionsApi {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @Override
  public ResponseEntity<TransactionResponse> createTransaction(TransactionRequest transactionRequest) {
    if (transactionRequest.getAmount() == null
        || transactionRequest.getFromAccountId() == null
        || transactionRequest.getToAccountId() == null
    ) {
      throw new ClientErrorException("One of the required fields are missing");
    }

    if (transactionRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new ClientErrorException("The amount must be a positive number");
    }

    try {
      var transactionEntity = transactionService.createTransaction(
          transactionRequest.getFromAccountId(),
          transactionRequest.getToAccountId(),
          transactionRequest.getAmount()
      );
      return new ResponseEntity<>(createResponseFromEntity(transactionEntity), HttpStatus.OK);
    } catch (AccountNotFoundException e) {
      throw new NotFoundException(e.getMessage());
    } catch (BalanceTooLowException e) {
      throw new ClientErrorException(e.getMessage());
    }
  }

  private TransactionResponse createResponseFromEntity(TransactionEntity transactionEntity) {
    return new TransactionResponse(
        transactionEntity.getTransactionId(),
        transactionEntity.getAmount()
    );
  }
}
