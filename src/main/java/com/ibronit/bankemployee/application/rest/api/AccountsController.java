package com.ibronit.bankemployee.application.rest.api;

import com.ibronit.bankemployee.application.rest.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class AccountsController implements AccountsApi {

  @Override
  public ResponseEntity<Void> createAccount() {
    return AccountsApi.super.createAccount();
  }

  @Override
  public ResponseEntity<Account> showAccountById(String accountId) {
    return AccountsApi.super.showAccountById(accountId);
  }
}
