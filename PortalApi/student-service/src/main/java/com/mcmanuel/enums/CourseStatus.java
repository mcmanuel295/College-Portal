package com.mcmanuel.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseStatus {
    COMPULSORY("C"),
    ELECTIVE("E");

    private final String status;
}
