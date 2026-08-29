package com.mcmanuel.domain.course;


import com.mcmanuel.domain.grade.Grade;
import com.mcmanuel.pojo.Lecturer;

import java.util.List;
import java.util.Map;

public interface CourseService{
    CourseDto createCourse(CourseDto course);

    List<CourseDto> getAllCourses(int pageNo, int pageSize);

    CourseDto getCourseByTitle(String courseTitle);

    CourseDto getCourseByCode(String courseCode);

    void sendNotification(String courseCode,String message);

    void sendGrade(String courseCode, Grade grade);

    CourseDto updateCourse(String courseTitle,CourseDto updatedCourse);

    boolean deleteCourseByTitle(String courseTitle);

    boolean deleteCourseByCode(String courseCode);

    List<String> getCourseStudents(String courseCode,int pageNo, int pageSize);

    String gradeStudents(String courseCode, Map<String,Double> grades);

    List<Lecturer> getAssignedLecturers(String courseCode);

    String assignedLecturers(String courseCode,List<String> staffNumbers);
}
