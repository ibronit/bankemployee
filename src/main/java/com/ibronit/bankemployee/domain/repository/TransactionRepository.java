package com.ibronit.bankemployee.domain.repository;

import com.ibronit.bankemployee.domain.model.TransactionEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, UUID> {

}
