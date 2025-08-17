package com.leukim.commander.infrastructure.controllers.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record OrderDto(
    @JsonProperty("id")
    @Schema(
        description = "Unique identifier of the order",
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    UUID id,

    @JsonProperty("name")
    @Schema(
        description = "Name of the client who placed the order",
        example = "John Doe"
    )
    String name,

    @JsonProperty("items")
    @Schema(
        description = "Map of product IDs to quantities",
        example = "{\"123e4567-e89b-12d3-a456-426614174000\": 2.0}"
    )
    Map<UUID, Double> items,

    @JsonProperty("picked")
    @Schema(
        description = "Indicates whether the order has been picked up",
        example = "false"
    )
    boolean picked
) {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public OrderDto {
        items = items != null ? items : new HashMap<>();
    }
}
