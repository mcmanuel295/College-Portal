package com.mcmanuel;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);

    }

}