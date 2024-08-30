package com.ibronit.bankemployee.application.rest.api;

import com.ibronit.bankemployee.application.rest.exception.RequiredFieldsAreMissingException;
import com.ibronit.bankemployee.application.rest.model.TransactionRequest;
import com.ibronit.bankemployee.application.rest.model.TransactionResponse;
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
      throw new RequiredFieldsAreMissingException();
    }

    if (transactionRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      // throw
    }

    var transactionEntity = transactionService.createTransaction(
        transactionRequest.getFromAccountId(),
        transactionRequest.getToAccountId(),
        transactionRequest.getAmount()
    );

    return new ResponseEntity<>(createResponseFromEntity(transactionEntity), HttpStatus.OK);
  }

  private TransactionResponse createResponseFromEntity(TransactionEntity transactionEntity) {
    return new TransactionResponse(
        transactionEntity.getTransactionId(),
        transactionEntity.getAmount()
    );
  }
}
