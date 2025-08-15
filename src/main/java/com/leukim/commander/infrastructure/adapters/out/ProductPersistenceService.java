package com.leukim.commander.infrastructure.adapters.out;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.out.ProductsPersistencePort;
import com.leukim.commander.infrastructure.mappers.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ProductPersistenceService implements ProductsPersistencePort {
    private final ProductMapper mapper;
    private final ProductRepository repository;

    public ProductPersistenceService(ProductMapper mapper, ProductRepository repository) {
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
    public Optional<Product> findById(String id) {
        return repository.findById(id).map(mapper::fromDbModel);
    }

    @Override
    public Product save(Product entity) {
        repository.save(mapper.toDbModel(entity));
        return entity;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
