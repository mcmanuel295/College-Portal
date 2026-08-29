package com.mcmanuel.domain.student;

import com.mcmanuel.enums.Department;
import com.mcmanuel.enums.Level;
import com.mcmanuel.enums.Role;
import com.mcmanuel.enums.Faculty;
import com.mcmanuel.pojo.Grade;
import com.mcmanuel.pojo.Notification;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
class Student{
    @Id
    @UuidGenerator
    private String studentId;

    @Column(name = "first_name", nullable = false)
    @Pattern(regexp = "^[A-Za-z]+$")
    @Size(max = 20)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    @Size(max = 20)
    @Pattern(regexp = "^[A-Za-z]+$")
    private String lastname;

    @Column(nullable = false)
    private String fullName;

    @Column(name = "matriculation_number",unique = true, nullable = false, length = 9)
    private String matriculationNumber;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated
    @Column(nullable = false)
    private Faculty faculty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Level level;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "course_codes")
    private Set<String> courseCodes = new HashSet<>();

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private double CGPA;

    @Column(nullable = false)
    private double GPA;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_grades", joinColumns = @JoinColumn(name = "student_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "matriculationNumber", column = @Column(name = "student_matric_no")),
            @AttributeOverride(name = "courseCode", column = @Column(name = "grade_course_code"))
    })
    private Set<Grade> semesterGrades;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    private String imageUrl;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "notifications", joinColumns = @JoinColumn(name = "student_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "matriculationNumber", column = @Column(name = "student_matric_no")),
    })
    @OrderBy(value = "timeReceived DESC")
    private List<Notification> notification = new ArrayList<>();



    private void fullName(){
        this.fullName =this.getLastname()+" "+this.getFirstname();
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        fullName();
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        fullName();
    }
}
