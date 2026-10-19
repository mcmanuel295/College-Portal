package com.mcmanuel.domain.staff;

import com.mcmanuel.enums.Department;
import com.mcmanuel.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Staff {
    @Pattern(regexp = "^[A-Za-z]+$")
    @Column(nullable = false)
    private String firstname;

    @Pattern(regexp = "^[A-Za-z]+$")
    @Column(nullable = false)
    private String lastname;

    private String fullName ;

    @Column(unique = true,nullable = false)
    private String staffId;

    @Email
    @Column(unique = true,nullable = false)
    private String email;

    @Pattern(regexp = "^(\\+?234 |0)(70|80|81|90|91)\\d{8}$",message = "Invalid Nigerian phone number")
    @Column(nullable = false)
    private String phoneNumber;

    private Department department;

//    @Enumerated
//    @Column(unique = true, nullable = false)
//    private Faculty faculty;

    private List<Role> roles = new ArrayList<>();
    @Column(nullable = false)
    private String password;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime dateCreated;

    private boolean isEnabled ;
    private String imageUrl;

    private void fullName(){
        this.fullName= this.getLastname()+" "+this.getFirstname();
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
