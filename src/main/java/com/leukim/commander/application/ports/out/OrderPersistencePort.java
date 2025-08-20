package com.leukim.commander.application.ports.out;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderPersistencePort {
    List<Order> getAll();

    List<Order> getByDate(LocalDate date);

    Optional<Order> findById(UUID id);

    Order create(CreateOrderDto createOrderDto);

    Order addItem(UUID order, Product product, double quantity);

    Order removeItem(Order order, UUID productId);

    Order pickUp(UUID id);

    void delete(UUID id);

    void deleteAll();
}
