package com.mcmanuel.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mcmanuel.enums.Department;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Lecturer {

    private String firstname;
    private String lastname;
    private String staffId;
    private String fullName;
    private String email;

    private String phoneNumber;

//    @Enumerated
//    @Column(unique = true, nullable = false)
//    private Faculty faculty;

//    private Set<Course> courses;
    private Department department;
//    private Role role;;
//    private LocalDateTime dateCreated;

}
