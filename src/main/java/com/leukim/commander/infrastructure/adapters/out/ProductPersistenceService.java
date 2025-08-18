package com.leukim.commander.infrastructure.adapters.out;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.out.ProductPersistencePort;
import com.leukim.commander.infrastructure.adapters.out.model.DbProduct;
import com.leukim.commander.infrastructure.mappers.ProductMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public final class ProductPersistenceService implements ProductPersistencePort {
    private final ProductMapper mapper;
    private final ProductRepository repository;

    public ProductPersistenceService(ProductMapper mapper,
                                     ProductRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<Product> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .map(mapper::fromDbModel)
            .toList();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return repository.findById(id).map(mapper::fromDbModel);
    }

    @Override
    public Product save(Product entity) {
        DbProduct createdProduct = repository.save(mapper.toDbModel(entity));
        return mapper.fromDbModel(createdProduct);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
