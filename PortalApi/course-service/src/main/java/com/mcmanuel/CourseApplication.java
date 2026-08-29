package com.mcmanuel;

import com.itextpdf.text.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@ConfigurationPropertiesScan
@EnableFeignClients
@SpringBootApplication
public class CourseApplication {
    public static void main(String[] args) throws IOException, DocumentException {
        SpringApplication.run(CourseApplication.class,args);
    }



}