package com.leukim.commander.infrastructure.adapters.out;

import com.leukim.commander.infrastructure.model.DbProduct;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<DbProduct, String> {
}
