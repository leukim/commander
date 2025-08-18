package com.leukim.commander.application.model;

import java.util.UUID;

public record Product(UUID id, String name, String description) {
}
