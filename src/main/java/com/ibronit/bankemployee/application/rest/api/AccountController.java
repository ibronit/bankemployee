package com.ibronit.bankemployee.application.rest.api;

import com.ibronit.bankemployee.application.rest.exception.NotFoundException;
import com.ibronit.bankemployee.application.rest.exception.ClientErrorException;
import com.ibronit.bankemployee.application.rest.model.AccountRequest;
import com.ibronit.bankemployee.application.rest.model.AccountResponse;
import com.ibronit.bankemployee.domain.model.AccountEntity;
import com.ibronit.bankemployee.domain.service.AccountService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController implements AccountsApi {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public ResponseEntity<AccountResponse> createAccount(AccountRequest newAccount) {
    if (newAccount.getBalance() == null) {
      throw new ClientErrorException("The balance field must be provided in the request body");
    }

    var accountEntity = accountService.createAccount(newAccount.getBalance());
    return new ResponseEntity<>(createResponseFromEntity(accountEntity), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<AccountResponse> getAccountById(UUID accountId) {
    var maybeAccountEntity = accountService.getAccountById(accountId);
    if (maybeAccountEntity.isEmpty()) {
      throw new NotFoundException("Account not found by the provided accountId");
    }

    return new ResponseEntity<>(createResponseFromEntity(maybeAccountEntity.get()), HttpStatus.OK);
  }

  private AccountResponse createResponseFromEntity(AccountEntity accountEntity) {
    return new AccountResponse(
        accountEntity.getAccountId(),
        accountEntity.getBalance()
    );
  }
}
