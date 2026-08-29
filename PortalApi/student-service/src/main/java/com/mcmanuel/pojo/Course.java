package com.mcmanuel.pojo;

import com.mcmanuel.enums.Level;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Embeddable
public class Course {
    private String courseTitle;
    private String courseCode;
    private Level level;
    private Integer unit;
    private char status;

}
