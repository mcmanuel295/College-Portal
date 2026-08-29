package com.mcmanuel.pojo;

import com.mcmanuel.domain.course.CourseDto;
import com.mcmanuel.enums.Level;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student{
    String firstname;

    @Column(name = "lastname", nullable = false)
    String lastname;

    String fullName;

    @Column(nullable = false, length = 9)
    String matriculationNumber;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    String email;

    Level level;

    @Enumerated
    @Column(unique = true, nullable = false)
    Faculty faculty;

    @Enumerated
    @Column(unique = true, nullable = false)
    Department department;

    @ManyToMany
    Set<CourseDto> courses;

//    @Column(nullable = false)
//    Role role,

    @Column(nullable = false)
    double CGPA;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    double GPA;

    @Column(nullable = false)
    LocalDateTime dateCreated;

}
