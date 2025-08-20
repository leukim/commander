package com.leukim.commander.application.ports.in;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.ports.in.model.AddOrderItemDto;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderManagementUseCase {
    List<Order> getAll();

    Optional<Order> findById(UUID id);

    Order create(CreateOrderDto createOrderDto);

    Order addItem(UUID orderId, AddOrderItemDto addOrderItemDto);

    Order removeItem(UUID orderId, UUID productId);

    Order pickUp(UUID id);

    void remove(UUID id);

    List<Order> getByDate(LocalDate date);
}
