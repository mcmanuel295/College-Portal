package com.mcmanuel.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "student-service",url = "http:localhost:8080/stuudents")
public interface StudentClient {
}
