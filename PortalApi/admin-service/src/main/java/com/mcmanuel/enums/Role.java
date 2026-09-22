package com.mcmanuel.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}
