package com.ibronit.bankemployee.domain.repository;

import com.ibronit.bankemployee.domain.model.AccountEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, UUID> {
  @Query("SELECT account_id, balance FROM account WHERE account_id = :accountId FOR UPDATE")
  Optional<AccountEntity> findByIdForUpdate(UUID accountId);
}
