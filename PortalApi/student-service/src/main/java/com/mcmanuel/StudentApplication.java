package com.mcmanuel;

import com.mcmanuel.domain.student.RegisterRequest;
import com.mcmanuel.domain.student.StudentDto;
import com.mcmanuel.domain.student.StudentService;
import com.mcmanuel.enums.Department;
import com.mcmanuel.enums.Faculty;
import com.mcmanuel.exception.StudentNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@ConfigurationPropertiesScan
@EnableScheduling
@EnableAsync
@EnableFeignClients
@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class StudentApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class,args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentService studentService){
        return arg -> {
            StudentDto student;
            try{
                student = studentService.getStudentByEmail("mcmanuel755@gmail.com");
                log.info("initial user entered");
                System.out.println("the student"+ student);
            }
            catch (StudentNotFoundException ex) {
                log.info("initial student entry");
                studentService.registerStudent("mcmanuel755@gmail.com",
                        RegisterRequest.builder()
                                .firstname("Emmanuel")
                                .lastname("Ogbu")
                                .faculty(Faculty.SCIENCE)
                                .department(Department.COMPUTER_SCIENCE)
                                .phoneNumber("09081199688")
//                            .file()
                                .createPassword("1234")
                                .confirmPassword("1234")
                                .build());
            }
        };
    }
}
