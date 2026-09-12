package com.mcmanuel.domain.course;

import com.mcmanuel.enums.Level;

public record CourseRequest(
            String courseTitle,
            String courseCode,
            Integer unit,
            Level level
){}
