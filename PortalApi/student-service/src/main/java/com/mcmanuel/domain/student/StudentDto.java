package com.mcmanuel.domain.student;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mcmanuel.enums.Department;
import com.mcmanuel.enums.Faculty;
import com.mcmanuel.enums.Level;
import com.mcmanuel.enums.Role;
import com.mcmanuel.pojo.Grade;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StudentDto (
        @Column(name = "firstname", nullable = false)
        String firstname,

        @Column(name = "lastname", nullable = false)
        String lastname,

        String fullName,

        @Column(name = "matriculation_Number", nullable = false, length = 9)
        String matriculationNumber,

        @Email
        @Column(name = "email", unique = true, nullable = false)
        String email,

        @Column(nullable = false)
        String phoneNumber,

        @Enumerated
        @Column(unique = true, nullable = false)
        Faculty faculty,

        @Enumerated
        @Column(unique = true, nullable = false)
        Department department,

        Level level,

        Set<String> courseCodes,

        @Column(nullable = false)
        Role role,

        @Column(nullable = false)
        double CGPA,

        @Column(nullable = false)
        double GPA,

        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "student_grades", joinColumns = @JoinColumn(name = "matriculation_Number"))
        @Column(name = "semester_grades")
        Set<Grade> semesterGrades,

        @Column(nullable = false)
        LocalDateTime dateCreated
){}
