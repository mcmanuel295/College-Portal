package com.mcmanuel.pojo;

import com.mcmanuel.enums.Department;
import com.mcmanuel.enums.Faculty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Faculty faculty;
    private Department department;
    private String createPassword;
    private String confirmPassword;
//    private MultipartFile file;
}
