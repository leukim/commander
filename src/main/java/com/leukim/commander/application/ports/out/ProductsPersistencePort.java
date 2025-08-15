package com.leukim.commander.application.ports.out;

import com.leukim.commander.application.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsPersistencePort {
    List<Product> getAll();

    Optional<Product> findById(String id);

    Product save(Product entity);

    void delete(String id);
}
