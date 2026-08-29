package com.mcmanuel.client;

import com.mcmanuel.pojo.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(name = "student-service", url = "${STUDENT_SERVICE_URL:localhost:8080/students}")
public interface StudentClient {

    @GetMapping("/{courseCode}")
    List<Student> getAllStudentsByCourse(String courseCode, @RequestParam(required = false,defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10",required = false) int pageSize);
}
