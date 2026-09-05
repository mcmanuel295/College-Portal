package com.mcmanuel.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {
    MECHANICAL_ENGINEERING(Faculty.ENGINEERING ,"Mechanical Engineering","001","MEG"),
    PHYSICS(Faculty.SCIENCES,"Physics","01","PHY"),
    CHEMISTRY(Faculty.SCIENCES,"Chemistry","04","CHM"),
    COMPUTER_SCIENCE(Faculty.SCIENCES,"Computer Science","05","CSC"),
    MATHEMATICS(Faculty.SCIENCES,"Mathematics","07","MAT");

    private final Faculty faculty;
    private final String name;
    private final String number;
    private final String code;

}
