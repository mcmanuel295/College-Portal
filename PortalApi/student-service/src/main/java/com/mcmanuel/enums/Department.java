package com.mcmanuel.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {
    MECHANICAL_ENGINEERING(Faculty.ENGINEERING ,"Mechanical Engineering","01"),
    PHYSICS(Faculty.SCIENCES,"Physics","01"),
    CHEMISTRY(Faculty.SCIENCES,"Chemistry","04"),
    COMPUTER_SCIENCE(Faculty.SCIENCES,"Computer Science","05"),
    MATHEMATICS(Faculty.SCIENCES,"Mathematics","07");

    private final Faculty faculty;
    private final String name;
    private final String code;


}
