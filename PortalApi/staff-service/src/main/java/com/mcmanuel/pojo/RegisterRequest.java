package com.mcmanuel.pojo;

import com.mcmanuel.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
