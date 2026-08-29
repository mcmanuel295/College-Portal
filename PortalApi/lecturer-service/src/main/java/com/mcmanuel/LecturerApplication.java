package com.mcmanuel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class LecturerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LecturerApplication.class,args);
    }
}