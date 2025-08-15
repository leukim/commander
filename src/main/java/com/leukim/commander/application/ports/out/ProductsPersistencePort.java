package com.leukim.commander.application.ports.out;

import com.leukim.commander.application.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductsPersistencePort {
    List<Product> getAll();

    Optional<Product> findById(UUID id);

    Product save(Product entity);

    void delete(UUID id);
}
