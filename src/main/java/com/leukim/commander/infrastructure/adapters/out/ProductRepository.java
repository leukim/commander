package com.leukim.commander.infrastructure.adapters.out;

import com.leukim.commander.infrastructure.adapters.out.model.DbProduct;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<DbProduct, UUID> {
}
