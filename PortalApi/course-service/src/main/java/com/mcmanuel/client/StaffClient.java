package com.mcmanuel.client;

import com.mcmanuel.pojo.Lecturer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "staff-service",url = "localhost:8081/lecturers")
public interface StaffClient {

    @GetMapping("/{staffId}")
    Lecturer findStaffByStaffId(@PathVariable String staffId);
}
