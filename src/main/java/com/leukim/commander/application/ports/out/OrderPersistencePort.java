package com.leukim.commander.application.ports.out;

import com.leukim.commander.application.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderPersistencePort {
    List<Order> getAll();

    Optional<Order> findById(UUID id);

    Order create(Order order);

    void delete(UUID id);
}
