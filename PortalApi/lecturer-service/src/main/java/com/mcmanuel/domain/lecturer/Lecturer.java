package com.mcmanuel.domain.lecturer;

import com.mcmanuel.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder@ToString
@Setter
class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String lecturerId;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String fullName = fullName();

    @Column(unique = true, nullable = false, length = 9)
    private String staffNumber;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

//    @Enumerated
//    @Column(unique = true, nullable = false)
//    private Faculty faculty;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Department department;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "assigned-courses",joinColumns = @JoinColumn(name = "lecturer_id"))
    private Set<String> courses;

    @Column(nullable = false)
    private Role role;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime dateCreated;


    private String imageUrl;

    private String fullName(){
        return this.getLastname()+" "+this.getFirstname();
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
