package com.mcmanuel.domain.course;

import com.mcmanuel.enums.Level;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID courseId ;

    @Column(unique = true,nullable =false)
    private String courseTitle;

    @Column(unique = true, nullable = false)
    private String courseCode;

    @Column(unique = true)
    private String fullTitle ;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_list",joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "course-students")
    private List<String> studentlist;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "assigned_lecturers",joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "course-lecturers")
    private List<String> AssignedLecturers;

    @Column(nullable = false)
    private Integer unit;

//    private char status;

    private String prerequisite;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Level level;

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
        fullTitle();
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
        fullTitle();
    }

    public void fullTitle(){
        this.fullTitle= this.courseCode+" "+this.courseTitle;
    }

}
