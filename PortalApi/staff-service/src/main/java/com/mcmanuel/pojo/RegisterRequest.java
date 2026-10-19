package com.mcmanuel.pojo;

import com.mcmanuel.enums.Department;
import com.mcmanuel.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Department department;
    private Status status;
    private String createPassword;
    private String confirmPassword;
    //    private MultipartFile file;
}
