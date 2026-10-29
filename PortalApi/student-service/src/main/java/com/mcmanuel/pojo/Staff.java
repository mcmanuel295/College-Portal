package com.mcmanuel.pojo;

import com.mcmanuel.enums.Department;
import lombok.Data;


@Data
public class Staff {
    private String firstname;
    private String lastname;
    private String fullName ;
    private String staffId;

    private Department department;

//    @Enumerated
//    @Column(unique = true, nullable = false)
//    private Faculty faculty;



}
