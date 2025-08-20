package com.leukim.commander.infrastructure.controllers;

import static com.leukim.commander.infrastructure.controllers.ApiConfig.API_BASE_PATH;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.ProductManagementUseCase;
import com.leukim.commander.application.ports.in.model.CreateProductDto;
import com.leukim.commander.infrastructure.controllers.exception.ProductNotFoundException;
import com.leukim.commander.infrastructure.controllers.model.ProductDto;
import com.leukim.commander.infrastructure.mappers.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(API_BASE_PATH + ProductManagementController.PRODUCT_BASE_PATH)
public final class ProductManagementController {
    public static final String PRODUCT_BASE_PATH = "/products";

    private final ProductManagementUseCase useCase;
    private final ProductMapper mapper;

    public ProductManagementController(ProductManagementUseCase useCase,
                                       ProductMapper mapper) {
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
    public ProductDto add(@RequestBody CreateProductDto createProductDto) {
        Product createdProduct = useCase.create(createProductDto);
        return mapper.toDto(createdProduct);
    }

    @Operation(summary = "Get product by ID", description = "Retrieves a product by its unique identifier.")
    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable UUID id) {
        return useCase.findById(id)
            .map(mapper::toDto)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Operation(summary = "Delete a product", description = "Deletes a product by its unique identifier.")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        if (useCase.findById(id).isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        useCase.remove(id);
    }
}
