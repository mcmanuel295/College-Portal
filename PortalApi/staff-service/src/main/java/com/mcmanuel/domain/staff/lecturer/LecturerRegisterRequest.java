package com.mcmanuel.domain.staff.lecturer;

import com.mcmanuel.enums.Department;
import com.mcmanuel.pojo.RegisterRequest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LecturerRegisterRequest extends RegisterRequest {
    private Department department;

}
