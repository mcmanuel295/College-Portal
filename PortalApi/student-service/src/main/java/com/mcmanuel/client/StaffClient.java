package com.mcmanuel.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service", url = "localhost:8081")
public interface StaffClient {

    @GetMapping("?")
    Stafff indStaffByStaffId(@PathVariable String staffId);
}
