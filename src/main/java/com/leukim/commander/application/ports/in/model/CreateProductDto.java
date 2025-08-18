package com.leukim.commander.application.ports.in.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateProductDto(
    @Schema(description = "Name of the product")
    String name,
    @Schema(description = "Description of the product")
    String description
) {
}
