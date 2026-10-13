package com.mcmanuel.domain.lecturer;

import com.mcmanuel.domain.staff.Staff;
import com.mcmanuel.enums.Department;
import com.mcmanuel.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.util.*;
@SuperBuilder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Lecturer extends Staff {
    @Id
    @UuidGenerator
    private UUID lecturerId;

    private Department department;

    Set<String> courses = new HashSet<>();

    @Override
    public void setRoles(List<Role> roles) {
        this.getRoles().addAll(roles);
    }
}

