package com.leukim.commander.application.ports.in;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateProductDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductManagementUseCase {
    List<Product> getAll();

    Optional<Product> findById(UUID id);

    Product create(CreateProductDto createProductDto);

    List<Product> bulkCreate(List<CreateProductDto> createProductDtos);

    void remove(UUID id);
}
