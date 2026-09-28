package com.mcmanuel;

import com.mcmanuel.domain.lecturer.LecturerService;
import com.mcmanuel.domain.staff.StaffService;
import com.mcmanuel.enums.Department;
import com.mcmanuel.exception.LecturerNotFoundException;
import com.mcmanuel.pojo.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@ConfigurationPropertiesScan
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
                System.out.println("Lecturer already initailized");
            }
            catch (LecturerNotFoundException ex){
                System.out.println("Lecturer Not Found, Creating initial lecturer");
                lecturerService.registerStaff("mcmanuel755@gmail.com",
                    RegisterRequest.builder()
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
