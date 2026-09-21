package com.mcmanuel.web;

import com.mcmanuel.domain.course.CourseDto;
import com.mcmanuel.domain.course.CourseRequest;
import com.mcmanuel.domain.course.CourseService;
import com.mcmanuel.domain.grade.Grade;
import com.mcmanuel.pojo.Lecturer;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(description = "Create Course Endpoint", summary = "Create course")
    ResponseEntity<CourseDto> createCourse(CourseRequest courseDto){
        return new ResponseEntity<>(courseService.createCourse(courseDto), HttpStatus.CREATED);
    }

    @Operation(description = "Get All Courses Endpoint", summary = "Gets all courses")
    @GetMapping("/")
    ResponseEntity<List<CourseDto>> getAllCourses(){
        return new ResponseEntity<>(courseService.getAllCourses(),HttpStatus.OK);
    }

    @Operation(description = "Get course by courseCode Endpoint", summary = "Get student taking course")
    @GetMapping("/{courseCode}/students")
    ResponseEntity<List<String>> getCourseStudents(@PathVariable String courseCode){
        List<String> list = courseService.getCourseStudents(courseCode);
        if (list == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @Operation(description = "Get course by title", summary = "Get course by course title")
    @GetMapping("/title")
    ResponseEntity<CourseDto> getCourseByTitle(@RequestParam String courseTitle){
        CourseDto dto=courseService.getCourseByTitle(courseTitle);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @Operation(description = "Get course by courseCode Endpoint", summary = "Get course by  course code")
    @GetMapping("/code")
    ResponseEntity<CourseDto> getCourseByCode(@RequestParam String courseCode){
        CourseDto dto=courseService.getCourseByCode(courseCode);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @Operation(description = "Update course", summary = "Update course")
    @PutMapping("/{courseCode}")
    ResponseEntity<CourseDto> updateCourse(@PathVariable String courseCode,@RequestBody CourseRequest courseRequest){
        CourseDto dto=courseService.updateCourse(courseCode,courseRequest);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @Operation(description = "Delete course", summary = "Delete course by title")
    @DeleteMapping("/{courseTitle}/title")
    ResponseEntity<String> deleteCourseByTitle(String courseTitle){
        boolean dto=courseService.deleteCourseByTitle(courseTitle);
        if (!dto) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }

    @Operation(description = "Delete course", summary = "Delete course by course code")
    @DeleteMapping("/{courseTitle}/code")
    ResponseEntity<String> deleteCourseByCode(String courseCode){
        boolean dto=courseService.deleteCourseByCode(courseCode);
        if (!dto) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }

    @Operation(description = "Send course notification", summary = "Send notification")
    @PostMapping("/{courseCode}/notification")
    ResponseEntity<String> sendNotification(@PathVariable String courseCode, @RequestBody String message){
        courseService.sendCourseNotification(courseCode,message);
        log.info("notification sent {}",message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(description = "Send course grade", summary = "Send the course grade to topic")
    @PostMapping("/{courseCode}/grade")
    ResponseEntity<String> sendGrade(@PathVariable String courseCode, @RequestBody Grade grade){
        courseService.sendGrade(courseCode,grade);
        log.info("grade sent {}",grade);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(description = "Grade students", summary = "Grades the course students")
    @PostMapping("/{courseCode}/grade-student")
    ResponseEntity<String> gradeStudents(@PathVariable String courseCode, @RequestBody Map<String,Double> grades){
        String graded = courseService.gradeStudents(courseCode,grades);
        if (graded == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }


    @Operation(description = "Get assigned lecturer", summary = "Get assigned lecturer to course")
    @PostMapping("/{courseCode}/assigned-lecturers")
    ResponseEntity<List<Lecturer>> getAssignedLecturers(@PathVariable String courseCode){
        List<Lecturer> lecturers = courseService.getAssignedLecturers(courseCode);
        if (lecturers == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lecturers,HttpStatus.OK);
    }

    @Operation(description = "Assign lecturer", summary = "Assigns lecturer to  course")
    @PostMapping("/{courseCode}/assign-lecturer")
    ResponseEntity<String> assignedLecturers(@PathVariable String courseCode,@RequestBody List<String> staffNumbers){
        String lecturers = courseService.assignedLecturers(courseCode,staffNumbers);
        if (lecturers == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Lecturers assigned",HttpStatus.OK);
    }

    @PostMapping("/{courseCode}/unassign-lecturer")
    @Operation(description = "Endpoint to unassign lecturer", summary = "Unassigns lecturer")
    ResponseEntity<String> unAssignedLecturers(@PathVariable String courseCode,@RequestBody List<String> staffNumbers){
        String lecturers = courseService.unAssignedLecturers(courseCode,staffNumbers);
        if (lecturers == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Lecturers assigned",HttpStatus.OK);
    }
}
