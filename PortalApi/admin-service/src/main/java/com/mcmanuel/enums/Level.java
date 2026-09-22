package com.mcmanuel.enums;

import lombok.Getter;

@Getter
public enum Level {
    SCHOOL(1),
    FACULTY(2),
    DEPARTMENT(3);

    private int stage;

    Level(int stage) {
        this.stage = stage;
    }
}
