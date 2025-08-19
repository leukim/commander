package com.leukim.commander.infrastructure.mappers;

import com.leukim.commander.application.model.Order;
import com.leukim.commander.application.model.Product;
import com.leukim.commander.application.ports.in.model.CreateOrderDto;
import com.leukim.commander.infrastructure.adapters.out.model.DbOrder;
import com.leukim.commander.infrastructure.adapters.out.model.DbProductQuantity;
import com.leukim.commander.infrastructure.controllers.model.OrderDto;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "picked", constant = "false")
    DbOrder create(CreateOrderDto createOrderDto);

    OrderDto toDto(Order model);

    List<OrderDto> toDtoList(List<Order> models);

    default Map<UUID, Double> mapToDtoItems(Map<Product, Double> items) {
        if (items == null) {
            return null;
        }
        return items.entrySet().stream()
            .collect(java.util.stream.Collectors.toMap(
                entry -> entry.getKey().id(),
                Map.Entry::getValue
            ));
    }

    default Order fromDbModel(DbOrder dbOrder, List<Product> allProducts) {
        if (dbOrder == null) {
            return null;
        }

        return new Order(
            dbOrder.getId(),
            dbOrder.getName(),
            mapItemsFromDb(dbOrder, allProducts),
            dbOrder.isPicked(),
            dbOrder.getDate()
        );
    }

    default Map<Product, Double> mapItemsFromDb(DbOrder dbOrder,
                                                List<Product> allProducts) {
        if (dbOrder.getItems() == null) {
            return null;
        }
        return dbOrder.getItems().stream()
            .collect(java.util.stream.Collectors.toMap(
                item -> allProducts.stream()
                    .filter(product -> product.id()
                        .equals(item.getProduct().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                        "Product not found: " + item.getProduct().getId())),
                DbProductQuantity::getQuantity
            ));
    }

}
