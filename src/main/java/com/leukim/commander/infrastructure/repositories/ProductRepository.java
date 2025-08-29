package com.leukim.commander.infrastructure.repositories;

import com.leukim.commander.infrastructure.repositories.model.DbProduct;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<DbProduct, UUID> {
}
