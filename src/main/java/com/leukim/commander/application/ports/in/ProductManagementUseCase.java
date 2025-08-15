package com.leukim.commander.application.ports.in;

import com.leukim.commander.application.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductManagementUseCase {
    List<Product> getAll();

    Optional<Product> findById(String id);

    Product add(Product entity);

    Product update(Product entity);

    void remove(String id);
}
