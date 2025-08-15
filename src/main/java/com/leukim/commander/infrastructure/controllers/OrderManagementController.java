package com.leukim.commander.infrastructure.controllers;

import com.leukim.commander.application.ports.in.OrderManagementUseCase;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.infrastructure.controllers.model.OrderDto;
import com.leukim.commander.infrastructure.mappers.OrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderManagementController {
    private final OrderManagementUseCase useCase;
    private final OrderMapper mapper;

    public OrderManagementController(OrderManagementUseCase useCase, OrderMapper mapper) {
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
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
    }

    @Operation(summary = "Delete an order", description = "Deletes an order by its unique identifier.")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        useCase.remove(id);
    }
}
