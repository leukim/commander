package com.leukim.commander.infrastructure.controllers.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductDto(
        @Schema(
                description = "Unique identifier of the product",
                example = "ASD123"
        )
        String id,
        @Schema(
                description = "Name of the product",
                example = "Apple Juice"
        )
        String name,
        @Schema(
                description = "Description of the product",
                example = "Freshly squeezed apple juice with no added sugar."
        )
        String description
) {
}