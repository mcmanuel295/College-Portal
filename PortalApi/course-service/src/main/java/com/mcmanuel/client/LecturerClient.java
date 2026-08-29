package com.mcmanuel.client;

import com.mcmanuel.pojo.Lecturer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "lecturer-service",url = "localhost:8081/lecturers")
public interface LecturerClient {

    @PostMapping("/{staffNumber}")
    Lecturer findLecturerByStaffId(@PathVariable String staffNumber);
}
