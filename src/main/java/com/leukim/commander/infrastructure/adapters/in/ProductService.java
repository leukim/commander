package com.leukim.commander.infrastructure.adapters.in;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.ProductManagementUseCase;
import com.leukim.commander.application.ports.in.model.CreateProductDto;
import com.leukim.commander.application.ports.out.ProductsPersistencePort;
import com.leukim.commander.infrastructure.mappers.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService implements ProductManagementUseCase {
    private final ProductsPersistencePort persistencePort;
    private final ProductMapper mapper;

    public ProductService(ProductsPersistencePort persistencePort, ProductMapper mapper) {
        this.persistencePort = persistencePort;
        this.mapper = mapper;
    }

    @Override
    public List<Product> getAll() {
        return persistencePort.getAll();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return persistencePort.findById(id);
    }

    @Override
    public Product create(CreateProductDto createProductDto) {
        Product product = mapper.create(createProductDto);
        return persistencePort.save(product);
    }

    @Override
    public void remove(UUID id) {
        persistencePort.delete(id);
    }
}
