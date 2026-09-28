package com.mcmanuel.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Faculty {
    ART("Art","01"),
    CLINICAL_SCIENCE("Clinical Science","02"),
    EDUCATION("Education","03"),
    ENGINEERING("Engineering","04"),
    ENVIRONMENTAL_SCIENCE("Environmental Science","05"),
    LAW("Law","06"),
    MANAGEMENT_SCINCE("Management Science","07"),
    SCIENCES("Sciences","08"),
    SOCIAL_SCIENCE("Social Science","09");

    private final String name;
    private final String code;

}
