package com.leukim.commander.infrastructure.adapters.in;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.ports.in.OrderManagementUseCase;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.application.ports.out.OrderPersistencePort;
import com.leukim.commander.infrastructure.mappers.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements OrderManagementUseCase {
    private final OrderPersistencePort persistencePort;
    private final OrderMapper mapper;

    public OrderService(OrderPersistencePort persistencePort, OrderMapper mapper) {
        this.persistencePort = persistencePort;
        this.mapper = mapper;
    }

    @Override
    public List<Order> getAll() {
        return persistencePort.getAll();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return persistencePort.findById(id);
    }

    @Override
    public Order create(CreateOrderDto createOrderDto) {
        Order order = mapper.create(createOrderDto);
        return persistencePort.create(order);
    }

    @Override
    public void remove(UUID id) {
        persistencePort.delete(id);
    }
}
