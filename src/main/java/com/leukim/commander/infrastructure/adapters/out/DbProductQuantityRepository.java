package com.leukim.commander.infrastructure.adapters.out;

import com.leukim.commander.infrastructure.adapters.out.model.DbProductQuantity;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DbProductQuantityRepository extends CrudRepository<DbProductQuantity, Long> {
    @Transactional
    void deleteByProductId(UUID productId);
}
