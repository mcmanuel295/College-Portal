package com.mcmanuel.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "course-service",url = "http:localhost:8081/courses")
public interface CourseClient {
}
