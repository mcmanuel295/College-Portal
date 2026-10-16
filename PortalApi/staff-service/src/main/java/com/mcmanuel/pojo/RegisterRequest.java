package com.mcmanuel.pojo;

import com.mcmanuel.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Department department;
    private String createPassword;
    private String confirmPassword;
    //    private MultipartFile file;
}
