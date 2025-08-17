package com.leukim.commander.infrastructure.adapters.out;

import com.leukim.commander.infrastructure.adapters.out.model.DbProductQuantity;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface DbProductQuantityRepository
    extends CrudRepository<DbProductQuantity, Long> {
    @Transactional
    void deleteByProductId(UUID productId);
}
