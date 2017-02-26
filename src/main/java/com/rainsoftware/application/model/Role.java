package com.rainsoftware.application.model;

public enum Role {
    ROLE_USER("User"),
    ROLE_ACTUATOR("Actuator Admin"),
    ROLE_ADMIN("Administrator");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
