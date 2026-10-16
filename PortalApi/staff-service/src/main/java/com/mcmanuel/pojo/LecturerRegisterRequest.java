package com.mcmanuel.pojo;

import com.mcmanuel.enums.Department;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Service
public class LecturerRegisterRequest extends RegisterRequest {
    private Department department;

}
