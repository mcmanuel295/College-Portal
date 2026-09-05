package com.mcmanuel.pojo;

import com.mcmanuel.enums.Department;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class RegisterRequest {
    private String firstname;
    private String lastname;
    //    private String email;
//    private String staffNumber;
    private Department department;
    private String phoneNumber;
    private String createPassword;
    private String ConfirmPassword;
    private MultipartFile file;
}
