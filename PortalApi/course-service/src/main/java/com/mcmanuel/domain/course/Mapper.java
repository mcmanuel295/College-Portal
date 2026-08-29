package com.mcmanuel.domain.course;

class Mapper {
    public static CourseDto toDto(Course course){
        return CourseDto.builder()
                .courseTitle(course.getCourseTitle())
                .courseCode(course.getCourseCode())
                .fullTitle(course.getFullTitle())
                .level(course.getLevel())
                .unit(course.getUnit())
                .studentList(course.getStudentlist())
                .assignedLecturers(course.getAssignedLecturers())
                .build();
    }

    public static Course toCourse(CourseDto dto){
        return Course.builder()
                .courseTitle(dto.courseTitle())
                .courseCode(dto.courseCode())
                .fullTitle(dto.fullTitle())
                .level(dto.level())
                .unit(dto.unit())
                .studentlist(dto.studentList())
                .AssignedLecturers(dto.assignedLecturers())
                .build();
    }
}
