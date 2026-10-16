package com.mcmanuel;

import com.mcmanuel.domain.staff.lecturer.LecturerService;
import com.mcmanuel.enums.Department;
import com.mcmanuel.exception.LecturerNotFoundException;
import com.mcmanuel.pojo.LecturerRegisterRequest;
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
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
@SpringBootApplication
public class StaffApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaffApplication.class,args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(LecturerService lecturerService){
        return args -> {
            try {
                lecturerService.findStaffByEmail("mcmanuel755@gmail.com");
                System.out.println("Lecturer already initialized");
            }
            catch (LecturerNotFoundException ex){
                System.out.println("Lecturer Not Found, Creating initial lecturer");
                lecturerService.registerStaff("mcmanuel755@gmail.com",
                    LecturerRegisterRequest.builder()
                            .firstname("John")
                            .lastname("Ogbu")
                            .department(Department.COMPUTER_SCIENCE)
                            .phoneNumber("09081199688")
                            .createPassword("1234")
                            .confirmPassword("1234")
                            .build()
            );
        }
    };
}
}
