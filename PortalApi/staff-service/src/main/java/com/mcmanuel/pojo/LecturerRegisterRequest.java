package com.mcmanuel.domain.staff.lecturer;

import com.mcmanuel.enums.Department;
import com.mcmanuel.pojo.RegisterRequest;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LecturerRegisterRequest extends RegisterRequest {
    private Department department;

}
