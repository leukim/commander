package com.leukim.commander.infrastructure.repositories.model;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return "ROLE_" + name;
    }
}
