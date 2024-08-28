package com.ibronit.bankemployee.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("account")
public class AccountEntity {
  @Id
  private UUID accountId;
  private BigDecimal balance;

  public AccountEntity(UUID accountId, BigDecimal balance) {
    this.accountId = accountId;
    this.balance = balance;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public BigDecimal getBalance() {
    return balance;
  }
}
