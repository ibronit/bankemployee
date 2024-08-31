package com.ibronit.bankemployee.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ibronit.bankemployee.domain.model.AccountEntity;
import com.ibronit.bankemployee.domain.repository.AccountRepository;
import com.ibronit.bankemployee.domain.repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
public class TransactionServiceTest {
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      "postgres:16-alpine"
  );

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired
  TransactionService transactionService;

  @Autowired
  TransactionRepository transactionRepository;

  @Autowired
  AccountRepository accountRepository;

  @BeforeEach
  void setUp() {
    transactionRepository.deleteAll();
    accountRepository.deleteAll();
  }

  @Test
  void shouldHandleConcurrentTransactionsProperly() throws InterruptedException {
    var senderAccount = accountRepository.save(new AccountEntity(null, BigDecimal.valueOf(30)));
    var receiverAccount = accountRepository.save(new AccountEntity(null, BigDecimal.ZERO));

    // Thread#1 tries to execute a transaction and lock the accounts
    var executor = Executors.newVirtualThreadPerTaskExecutor();
    executor.execute(() -> {
      transactionService.createTransaction(senderAccount.getAccountId(), receiverAccount.getAccountId(), BigDecimal.valueOf(10));
    });

    // Thread#2 tries to execute another transaction and lock the account too
    executor.execute(() -> {
      transactionService.createTransaction(senderAccount.getAccountId(), receiverAccount.getAccountId(), BigDecimal.valueOf(20));
    });
    executor.awaitTermination(1, TimeUnit.SECONDS);

    var updatedSenderAccount = accountRepository.findById(senderAccount.getAccountId());
    assertTrue(updatedSenderAccount.isPresent());
    assertEquals(0, updatedSenderAccount.get().getBalance().compareTo(BigDecimal.valueOf(0)), "The balance of the sender account should be 0");

    var updatedReceiverAccount = accountRepository.findById(receiverAccount.getAccountId());
    assertTrue(updatedReceiverAccount.isPresent());
    assertEquals(0, updatedReceiverAccount.get().getBalance().compareTo(BigDecimal.valueOf(30)), "The balance of the receiver account should be 30");
  }
}
