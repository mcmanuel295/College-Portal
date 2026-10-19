package com.mcmanuel.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    TEACHING("Teaching"),NON_TEACHING("Non_Teaching");

    private final String status;

}
