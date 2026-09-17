package com.mcmanuel.domain.course;


import com.mcmanuel.domain.grade.Grade;
import com.mcmanuel.pojo.Lecturer;

import java.util.List;
import java.util.Map;

public interface CourseService{
    CourseDto createCourse(CourseRequest course);

    List<CourseDto> getAllCourses();

    CourseDto getCourseByTitle(String courseTitle);

    CourseDto getCourseByCode(String courseCode);

    boolean deleteCourseByTitle(String courseTitle);

    boolean deleteCourseByCode(String courseCode);

    CourseDto updateCourse(String courseTitle,CourseRequest courseRequest);

    void sendCourseNotification(String courseCode,String message);

    void sendGrade(String courseCode, Grade grade);

    List<String> getCourseStudents(String courseCode);

    String gradeStudents(String courseCode, Map<String,Double> grades);

    List<Lecturer> getAssignedLecturers(String courseCode);

    String assignedLecturers(String courseCode,List<String> staffNumbers);

    String unAssignedLecturers(String courseCode,List<String> staffNumbers);
}

