package com.leukim.commander.infrastructure.adapters.in;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.OrderManagementUseCase;
import com.leukim.commander.application.ports.in.ProductManagementUseCase;
import com.leukim.commander.application.ports.in.model.AddOrderItemDto;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.application.ports.out.OrderPersistencePort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public final class OrderManagementService implements OrderManagementUseCase {
    private final ProductManagementUseCase productManagementUseCase;
    private final OrderPersistencePort persistencePort;

    public OrderManagementService(
        ProductManagementUseCase productManagementUseCase,
        OrderPersistencePort persistencePort) {
        this.productManagementUseCase = productManagementUseCase;
        this.persistencePort = persistencePort;
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
        return persistencePort.create(createOrderDto);
    }

    @Override
    public Order addItem(UUID orderId, AddOrderItemDto addOrderItemDto) {
        Product product =
            productManagementUseCase.findById(addOrderItemDto.productId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Product not found with id: " + addOrderItemDto.productId()
                    )
                );

        return persistencePort.addItem(orderId, product,
            addOrderItemDto.quantity());
    }

    @Override
    public Order removeItem(UUID orderId, UUID productId) {
        Order order = findById(orderId).orElseThrow(
            () -> new IllegalArgumentException(
                "Order not found with id: " + orderId));
        return persistencePort.removeItem(order, productId);
    }

    @Override
    public void remove(UUID id) {
        persistencePort.delete(id);
    }
}
