package com.mcmanuel.enums;

import lombok.Getter;

@Getter
public enum Level {
    LEVEL100("100L"),
    LEVEL200("200L"),
    LEVEL300("300L"),
    LEVEL400("400L"),
    LEVEL500("500L");

    private final String level;

    Level(String value) {
        this.level =value;
    }
}
