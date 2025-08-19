package com.leukim.commander.application.ports.in.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record CreateOrderDto(
    @Schema(description = "Name of the order")
    String name,
    @Schema(description = "Date of the order")
    LocalDate date
) {
}
