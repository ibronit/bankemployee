package com.ibronit.bankemployee.domain.service;

import com.ibronit.bankemployee.domain.model.AccountEntity;
import com.ibronit.bankemployee.domain.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public AccountEntity createAccount(BigDecimal balance) {
    return accountRepository.save(new AccountEntity(null, balance));
  }

  public Optional<AccountEntity> getAccountById(UUID id) {
    return accountRepository.findById(id);
  }
}
