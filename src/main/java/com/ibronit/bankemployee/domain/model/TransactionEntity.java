package com.ibronit.bankemployee.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("transaction")
public class TransactionEntity {
  @Id
  private final UUID transactionId;
  private final UUID fromAccountId;
  private final UUID toAccountId;
  private final BigDecimal amount;

  public TransactionEntity(UUID transactionId, UUID fromAccountId, UUID toAccountId, BigDecimal amount) {
    this.transactionId = transactionId;
    this.fromAccountId = fromAccountId;
    this.toAccountId = toAccountId;
    this.amount = amount;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public UUID getFromAccountId() {
    return fromAccountId;
  }

  public UUID getToAccountId() {
    return toAccountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
