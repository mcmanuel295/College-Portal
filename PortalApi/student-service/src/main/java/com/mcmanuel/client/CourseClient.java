package com.mcmanuel.client;

import com.mcmanuel.pojo.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(url = "${COURSE_SERVICE_URL:localhost://8082/courses}",name = "course-service")
public interface CourseClient {

    @GetMapping("/{code}/code")
    Course getCourseByCode(@PathVariable String courseCode);

    @GetMapping("/{courseCode}")
    List<String> getCourseStudents(@PathVariable String courseCode);
}

