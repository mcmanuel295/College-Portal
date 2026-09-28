package com.mcmanuel.pojo;

import com.mcmanuel.enums.Department;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String firstname;
    private String lastname;
    //    private String email;
//    private String staffNumber;
    private Department department;
    private String phoneNumber;
    private String createPassword;
    private String confirmPassword;
//    private MultipartFile file;
}
