package com.leukim.commander.infrastructure.controllers.model;

import com.leukim.commander.application.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;
import java.util.UUID;

public record OrderDto(
        @Schema(
                description = "Unique identifier of the order",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,
        @Schema(
                description = "Name of the client who placed the order",
                example = "John Doe"
        )
        String name,
        @Schema(
                description = "Map of products in the order with their quantities",
                example = "{ \"ASD123\": 2.0, \"XYZ456\": 1.5 }" // TODO REVIEW
        )
        Map<Product, Double> items,
        @Schema(
                description = "Indicates whether the order has been picked up",
                example = "false"
        )
        boolean picked
) {
}
