package com.leukim.commander.application.ports.in.model;

import java.util.UUID;

public record AddOrderItemDto(UUID productId, int quantity) {
}

