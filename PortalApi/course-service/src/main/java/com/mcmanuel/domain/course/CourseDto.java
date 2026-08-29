package com.mcmanuel.domain.course;

import com.mcmanuel.enums.Level;
import lombok.Builder;

import java.util.List;

@Builder
public record CourseDto(
        String courseTitle,
        String courseCode,
        String fullTitle,
        Integer unit,
        Level level,
        List<String> studentList,
        List<String> assignedLecturers
){
}
