package com.leukim.commander.infrastructure.controllers;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.ProductManagementUseCase;
import com.leukim.commander.infrastructure.controllers.model.ProductDto;
import com.leukim.commander.infrastructure.mappers.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/products")
public class ProductManagementController {
    private final ProductManagementUseCase useCase;
    private final ProductMapper mapper;

    public ProductManagementController(ProductManagementUseCase useCase, ProductMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all products", description = "Retrieves a list of all products.")
    @GetMapping()
    public List<ProductDto> getAll() {
        return mapper.toDtoList(useCase.getAll());
    }

    @Operation(summary = "Add a new product", description = "Creates a new product with the provided details.")
    @PostMapping()
    public ProductDto add(ProductDto productDto) {
        Product createdProduct = useCase.add(mapper.fromDto(productDto));
        return mapper.toDto(createdProduct);
    }

    @Operation(summary = "Get product by ID", description = "Retrieves a product by its unique identifier.")
    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable String id) {
        return useCase.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Operation(summary = "Update an existing product", description = "Updates the details of an existing product.")
    @PutMapping("/{id}")
    public ProductDto update(@PathVariable String id, @RequestBody ProductDto productDto) {
        // TODO validate id is not modified
        return mapper.toDto(useCase.update(mapper.fromDto(productDto)));
    }

    @Operation(summary = "Delete a product", description = "Deletes a product by its unique identifier.")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        useCase.remove(id);
    }
}
