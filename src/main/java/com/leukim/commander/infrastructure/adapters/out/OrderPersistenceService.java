package com.leukim.commander.infrastructure.adapters.out;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.ports.out.OrderPersistencePort;
import com.leukim.commander.infrastructure.mappers.OrderMapper;
import com.leukim.commander.infrastructure.adapters.out.model.DbOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
public class OrderPersistenceService implements OrderPersistencePort {
    private final OrderMapper mapper;
    private final OrderRepository repository;

    public OrderPersistenceService(OrderMapper mapper, OrderRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<Order> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::fromDbModel)
                .toList();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return repository.findById(id).map(mapper::fromDbModel);
    }

    @Override
    public Order save(Order order) {
        DbOrder savedOrder = repository.save(mapper.toDbModel(order));
        return mapper.fromDbModel(savedOrder);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
