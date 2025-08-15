package com.leukim.commander.infrastructure.mappers;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.infrastructure.adapters.out.model.DbOrder;
import com.leukim.commander.infrastructure.adapters.out.model.DbProduct;
import com.leukim.commander.infrastructure.adapters.out.model.DbProductQuantity;
import com.leukim.commander.infrastructure.controllers.model.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OrderMapper implements EntityMapper<OrderDto, Order, DbOrder> {
    @Autowired
    private ProductMapper productMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "picked", constant = "false")
    public abstract Order create(CreateOrderDto createOrderDto);

    public Map<UUID, Double> mapItems(List<DbProductQuantity> items) {
        if (items == null || items.isEmpty()) {
            return Map.of();
        }
        return items.stream()
                .collect(Collectors.toMap(
                pq -> pq.getProduct().getId(),
                DbProductQuantity::quantity
        ));
    }

    public Map<Product, Double> mapFromDbItems(Map<UUID, Double> items) {
        if (items == null || items.isEmpty()) {
            return Map.of();
        }

        return items.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> productMapper.fromDbModel(new DbProduct(entry.getKey(), null, null)), // TODO test this works well
                        Map.Entry::getValue
                ));
    }

    public Map<UUID, Double> mapToDbItems(Map<Product, Double> items) {
        if (items == null || items.isEmpty()) {
            return Map.of();
        }

        return items.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().id(),
                        Map.Entry::getValue
                ));
    }

    public List<DbProductQuantity> mapItems(Map<UUID, Double> items) {
        if (items == null || items.isEmpty()) {
            return List.of();
        }

        return items.entrySet().stream()
                .map(entry -> new DbProductQuantity(
                        productMapper.toDbModel(new Product(entry.getKey(), null, null)), // TODO test this works well
                        entry.getValue()))
                .collect(Collectors.toList());
    }
}
