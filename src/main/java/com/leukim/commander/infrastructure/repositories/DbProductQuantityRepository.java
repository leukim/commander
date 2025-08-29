package com.leukim.commander.infrastructure.repositories;

import com.leukim.commander.infrastructure.repositories.model.DbProductQuantity;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface DbProductQuantityRepository
    extends CrudRepository<DbProductQuantity, Long> {
    @Transactional
    void deleteByProductId(UUID productId);
}
