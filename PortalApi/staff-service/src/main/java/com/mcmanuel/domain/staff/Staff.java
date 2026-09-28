package com.mcmanuel.domain.staff;

import com.mcmanuel.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Staff {
    private String firstname;
    private String lastname;
    private String fullName = fullName();
    private String staffNumber;
    private String email;
    private String phoneNumber;

//    @Enumerated
//    @Column(unique = true, nullable = false)
//    private Faculty faculty;

    private List<Role> roles =new ArrayList<>();
    private String password;
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
