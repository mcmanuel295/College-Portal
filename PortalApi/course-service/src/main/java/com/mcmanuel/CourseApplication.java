package com.mcmanuel;

import com.itextpdf.text.*;
import com.mcmanuel.domain.course.CourseDto;
import com.mcmanuel.domain.course.CourseRequest;
import com.mcmanuel.domain.course.CourseService;
import com.mcmanuel.enums.Level;
import com.mcmanuel.exception.CourseNotFoundException;
import jakarta.persistence.PrePersist;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@ConfigurationPropertiesScan
@EnableFeignClients
@SpringBootApplication
public class CourseApplication {
    public static void main(String[] args) throws IOException, DocumentException {
        SpringApplication.run(CourseApplication.class,args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(CourseService courseService){
        return args ->{
            try{
              courseService.getCourseByCode("CSC 101") ;
              courseService.getCourseByCode("MAT 101") ;
              courseService.getCourseByCode("CSC 102");
              courseService.getCourseByCode("CSC 103") ;
            }
            catch (CourseNotFoundException ex) {
                courseService.createCourse(new CourseRequest("INTRODUCTION TO PYTHON", "CSC 103", 3, Level.LEVEL100));
                courseService.createCourse(new CourseRequest("INTRODUCTION TO PROGRAMMING", "CSC 101", 3, Level.LEVEL100));
                courseService.createCourse(new CourseRequest("INTRODUCTION TO DISCRETE MATHEMATICS", "CSC 102", 3, Level.LEVEL100));
                courseService.createCourse(new CourseRequest("INTRODUCTION TO ALGEBRA", "MAT 101", 3, Level.LEVEL100));
            }
        };
    }



}