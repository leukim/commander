package com.leukim.commander.application.model;

import java.util.Map;
import java.util.UUID;

public record Order(UUID id, String name, Map<Product, Double> items, boolean picked) {
}
