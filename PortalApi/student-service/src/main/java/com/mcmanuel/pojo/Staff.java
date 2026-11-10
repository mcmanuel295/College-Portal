package com.mcmanuel.pojo;

import com.mcmanuel.enums.Department;
import com.mcmanuel.enums.Role;
import lombok.Data;

import java.util.List;


@Data
public class Staff {
    private String firstname;
    private String lastname;
    private String fullName ;
    private String staffId;

    private Department department;

    private List<Role> roles;

//    @Enumerated
//    @Column(unique = true, nullable = false)
//    private Faculty faculty;



}
