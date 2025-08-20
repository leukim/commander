package com.leukim.commander.infrastructure.controllers;

import static com.leukim.commander.infrastructure.controllers.ApiConfig.API_BASE_PATH;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.ports.in.OrderManagementUseCase;
import com.leukim.commander.application.ports.in.model.AddOrderItemDto;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.infrastructure.controllers.exception.OrderNotFoundException;
import com.leukim.commander.infrastructure.controllers.model.OrderDto;
import com.leukim.commander.infrastructure.mappers.OrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_BASE_PATH + OrderManagementController.ORDER_BASE_PATH)
public final class OrderManagementController {
    public static final String ORDER_BASE_PATH = "/orders";

    private final OrderManagementUseCase useCase;
    private final OrderMapper mapper;

    public OrderManagementController(OrderManagementUseCase useCase,
                                     OrderMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all orders", description = "Retrieves a list of all orders.")
    @GetMapping()
    public List<OrderDto> getAll() {
        return mapper.toDtoList(useCase.getAll());
    }

    @Operation(summary = "Create a new order", description = "Creates a new empty order with the provided details.")
    @PostMapping
    public OrderDto create(@RequestBody CreateOrderDto createOrderDto) {
        return mapper.toDto(useCase.create(createOrderDto));
    }

    @Operation(summary = "Get order by ID", description = "Retrieves an order by its unique identifier.")
    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable UUID id) {
        return useCase.findById(id)
            .map(mapper::toDto)
            .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Operation(summary = "Add item to order", description = "Adds an item to the specified order.")
    @PostMapping("/{orderId}/items")
    public OrderDto addItem(@PathVariable UUID orderId,
                            @RequestBody AddOrderItemDto addOrderItemDto) {
        Order order = useCase.addItem(orderId, addOrderItemDto);
        return mapper.toDto(order);
    }

    @Operation(summary = "Remove item from order", description = "Removes an item from the specified order.")
    @DeleteMapping("/{orderId}/items/{productId}")
    public OrderDto removeItem(@PathVariable UUID orderId,
                               @PathVariable UUID productId) {
        Order order = useCase.removeItem(orderId, productId);
        return mapper.toDto(order);
    }

    @Operation(summary = "Pick up an order", description = "Marks an order as picked up by its unique identifier.")
    @PostMapping("/{id}/pickup")
    public OrderDto pickUp(@PathVariable UUID id) {
        return mapper.toDto(useCase.pickUp(id));
    }

    @Operation(summary = "Delete an order", description = "Deletes an order by its unique identifier.")
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable UUID id) {
        if (useCase.findById(id).isEmpty()) {
            throw new OrderNotFoundException(id);
        }
        useCase.remove(id);
    }

    @Operation(summary = "Get orders by date", description = "Retrieves a list of orders for a specific date.")
    @GetMapping("/date/{date}")
    public List<OrderDto> getByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return mapper.toDtoList(useCase.getByDate(date));
    }
}
