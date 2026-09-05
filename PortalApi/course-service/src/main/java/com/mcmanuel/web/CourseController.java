package com.mcmanuel.web;

import com.mcmanuel.domain.course.CourseDto;
import com.mcmanuel.domain.course.CourseService;
import com.mcmanuel.domain.grade.Grade;
import com.mcmanuel.pojo.Lecturer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/")
    ResponseEntity<CourseDto> createCourse(CourseDto courseDto){
        return new ResponseEntity<>(courseService.createCourse(courseDto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    ResponseEntity<List<CourseDto>> getAllCourses(int pageNo, int pageSize){
        return new ResponseEntity<>(courseService.getAllCourses(pageNo,pageSize),HttpStatus.OK);
    }

    @GetMapping("/{courseCode}/students")
    ResponseEntity<List<String>> getCourseStudents(@PathVariable String courseCode, int pageNo, int pageSize){
        List<String> list = courseService.getCourseStudents(courseCode,pageNo,pageSize);
        if (list == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/{courseTitle}/title")
    ResponseEntity<CourseDto> getCourseByTitle(@PathVariable String courseTitle){
        CourseDto dto=courseService.getCourseByTitle(courseTitle);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/{courseCde}/code")
    ResponseEntity<CourseDto> getCourseByCode(@PathVariable String courseCode){
        CourseDto dto=courseService.getCourseByCode(courseCode);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/{courseCode}")
    ResponseEntity<CourseDto> updateCourse(@PathVariable String courseCode,@RequestBody CourseDto updatedCourse){
        CourseDto dto=courseService.updateCourse(courseCode,updatedCourse);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/{courseTitle}/title")
    ResponseEntity<String> deleteCourseByTitle(String courseTitle){
        boolean dto=courseService.deleteCourseByTitle(courseTitle);
        if (!dto) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }

    @DeleteMapping("/{courseTitle}/code")
    ResponseEntity<String> deleteCourseByCode(String courseCode){
        boolean dto=courseService.deleteCourseByCode(courseCode);
        if (!dto) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("deletef",HttpStatus.OK);
    }

    @PostMapping("/{courseCode}/notification")
    ResponseEntity<String> sendNotification(@PathVariable String courseCode, @RequestBody String message){
        courseService.sendNotification(courseCode,message);
        log.info("notification sent {}",message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{courseCode}/grade")
    ResponseEntity<String> sendGrade(@PathVariable String courseCode, @RequestBody Grade grade){
        courseService.sendGrade(courseCode,grade);
        log.info("grade sent {}",grade);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{courseCode}/grade-student")
    ResponseEntity<String> gradeStudents(@PathVariable String courseCode, @RequestBody Map<String,Double> grades){
        String graded = courseService.gradeStudents(courseCode,grades);
        if (graded == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }


    @PostMapping("/{courseCode}/assigned-lecturers")
    ResponseEntity<List<Lecturer>> getAssignedLecturers(@PathVariable String courseCode){
        List<Lecturer> lecturers = courseService.getAssignedLecturers(courseCode);
        if (lecturers == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lecturers,HttpStatus.OK);
    }

    @PostMapping("/{courseCode}")
    ResponseEntity<String> assignedLecturers(@PathVariable String courseCode,@RequestBody List<String> staffNumbers){
        String lecturers = courseService.assignedLecturers(courseCode,staffNumbers);
        if (lecturers == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Lecturers assigned",HttpStatus.OK);
    }
}
