package com.leukim.commander.infrastructure.adapters.in;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.ProductManagementUseCase;
import com.leukim.commander.application.ports.in.model.CreateProductDto;
import com.leukim.commander.application.ports.out.ProductPersistencePort;
import com.leukim.commander.infrastructure.mappers.ProductMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public final class ProductManagementService
    implements ProductManagementUseCase {
    private final ProductPersistencePort persistencePort;
    private final ProductMapper mapper;

    public ProductManagementService(ProductPersistencePort persistencePort,
                                    ProductMapper mapper) {
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
