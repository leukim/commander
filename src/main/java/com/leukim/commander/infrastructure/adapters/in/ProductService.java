package com.leukim.commander.infrastructure.adapters.in;

import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.ProductManagementUseCase;
import com.leukim.commander.application.ports.out.ProductsPersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductManagementUseCase {
    private final ProductsPersistencePort persistencePort;

    public ProductService(ProductsPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public List<Product> getAll() {
        return persistencePort.getAll();
    }

    @Override
    public Optional<Product> findById(String id) {
        return persistencePort.findById(id);
    }

    @Override
    public Product add(Product entity) {
        return persistencePort.save(entity);
    }

    @Override
    public Product update(Product entity) {
        return persistencePort.save(entity);
    }

    @Override
    public void remove(String id) {
        persistencePort.delete(id);
    }
}
