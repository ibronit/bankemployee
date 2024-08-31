package com.ibronit.bankemployee.domain.service;

import com.ibronit.bankemployee.application.rest.exception.NotFoundException;
import com.ibronit.bankemployee.domain.exception.BalanceTooLowException;
import com.ibronit.bankemployee.domain.model.AccountEntity;
import com.ibronit.bankemployee.domain.model.TransactionEntity;
import com.ibronit.bankemployee.domain.repository.AccountRepository;
import com.ibronit.bankemployee.domain.repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;

  public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
  }

  @Transactional
  public TransactionEntity createTransaction(UUID fromAccountId, UUID toAccountId, BigDecimal amount) {
    var maybeFromAccount = accountRepository.findByIdForUpdate(fromAccountId);
    if (maybeFromAccount.isEmpty()) {
      throw new NotFoundException("Sender account not found");
    }
    var fromAccount = maybeFromAccount.get();
    if (fromAccount.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
      throw new BalanceTooLowException("The balance of the sender account would be too low after the transaction");
    }

    var maybeToAccount = accountRepository.findByIdForUpdate(toAccountId);
    if (maybeToAccount.isEmpty()) {
      throw new NotFoundException("Receiver account not found");
    }
    var toAccount = maybeToAccount.get();

    var transaction = new TransactionEntity(null, fromAccountId, toAccountId, amount);
    var updatedFromAccount = new AccountEntity(fromAccountId, fromAccount.getBalance().subtract(amount));
    var updatedToAccount = new AccountEntity(toAccountId, toAccount.getBalance().add(amount));
    accountRepository.save(updatedFromAccount);
    accountRepository.save(updatedToAccount);
    return transactionRepository.save(transaction);
  }
}
