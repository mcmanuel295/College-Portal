package com.mcmanuel.client;

import com.mcmanuel.pojo.Staff;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "staff-service", url = "localhost:8081/staff")
public interface StaffClient {

    @GetMapping("/staffs")
    Staff findStaffClient(@PathVariable String staffId);


}
