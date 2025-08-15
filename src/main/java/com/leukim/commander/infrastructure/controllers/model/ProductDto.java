package com.leukim.commander.infrastructure.controllers.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ProductDto(
        @JsonProperty("id")
        @Schema(
                description = "Unique identifier of the product",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,

        @JsonProperty("name")
        @Schema(
                description = "Name of the product",
                example = "Apple Juice"
        )
        String name,

        @JsonProperty("description")
        @Schema(
                description = "Description of the product",
                example = "Freshly squeezed apple juice with no added sugar."
        )
        String description
) {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductDto {
        // Validate fields if needed
    }
}