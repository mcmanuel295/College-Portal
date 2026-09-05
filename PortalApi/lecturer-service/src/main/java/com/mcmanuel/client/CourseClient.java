package com.mcmanuel.client;

import com.mcmanuel.pojo.Grade;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "course-service",url = "http:localhost:8081/courses")
public interface CourseClient {

    @GetMapping("/{courseCode}/students")
    List<String> getCourseStudents(@PathVariable String courseCode, @RequestParam(required = false,defaultValue = "0") int pageNo, @RequestParam(required = false,defaultValue = "10") int pageSize);

    @PostMapping("/{courseCode}/notification")
    String sendNotification(@PathVariable String courseCode, @RequestBody String message);

    @PostMapping("/{courseCode}/grade")
    String sendGrade(@PathVariable String courseCode, @RequestBody Grade grade);

    @PostMapping("/{courseCode}/grade-student")
    String gradeStudents(@PathVariable String courseCode, @RequestBody Map<String,Double> grades);
}
