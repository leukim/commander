package com.leukim.commander.infrastructure.mappers;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.infrastructure.adapters.out.model.DbOrder;
import com.leukim.commander.infrastructure.adapters.out.model.DbProductQuantity;
import com.leukim.commander.infrastructure.controllers.model.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OrderMapper implements EntityMapper<OrderDto, Order, DbOrder> {
    @Autowired
    private ProductMapper productMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "picked", constant = "false")
    public abstract Order create(CreateOrderDto createOrderDto);

    public Map<Product, Double> mapItems(List<DbProductQuantity> items) {
        if (items == null || items.isEmpty()) {
            return Map.of();
        }
        return items.stream()
                .collect(Collectors.toMap(
                pq -> productMapper.fromDbModel(pq.getProduct()),
                DbProductQuantity::quantity
        ));
    }

    public List<DbProductQuantity> mapItems(Map<Product, Double> items) {
        if (items == null || items.isEmpty()) {
            return List.of();
        }

        return items.entrySet().stream()
                .map(entry -> new DbProductQuantity(productMapper.toDbModel(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
    }
}
