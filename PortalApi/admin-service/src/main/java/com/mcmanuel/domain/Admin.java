package com.mcmanuel.domain;

import com.mcmanuel.enums.Level;
import com.mcmanuel.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @UuidGenerator
    private UUID id;
    private String firstname;
    private String lastname;
    private String fullName;
    private Role role;
    private List<Level> level;

    @Column(unique = true)
    private String email;
    private String password;

    public void setFirstName(String firstname){
        this.firstname = firstname;
        setFullName();
    }

    public void setLastName(String lastname){
        this.lastname = lastname;
        setFullName();

    }

    public void setFullName(){
        this.fullName =this.lastname+" "+this.firstname;
    }
}
