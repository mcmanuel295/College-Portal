package com.mcmanuel.domain.student;

public class Mapper {

    static StudentDto toDto(Student student){
        return StudentDto.builder()
                .firstname(student.getFirstname())
                .lastname(student.getLastname())
                .fullName(student.getFullName())
                .email(student.getEmail())
                .department(student.getDepartment())
                .phoneNumber(student.getPhoneNumber())
                .level(student.getLevel())
                .faculty(student.getFaculty())
                .matriculationNumber(student.getMatriculationNumber())
                .courseCodes(student.getCourseCodes())
                .role(student.getRole())
                .GPA(student.getGPA())
                .CGPA(student.getCGPA())
                .dateCreated(student.getDateCreated())
                .build();
    }

    static Student toStudent(StudentDto dto){
        return Student.builder()
                .firstname(dto.firstname())
                .lastname(dto.lastname())
                .fullName(dto.fullName())
                .email(dto.email())
                .department(dto.department())
                .phoneNumber(dto.phoneNumber())
                .level(dto.level())
                .faculty(dto.faculty())
                .matriculationNumber(dto.matriculationNumber())
                .courseCodes(dto.courseCodes())
                .role(dto.role())
                .CGPA(dto.CGPA())
                .GPA(dto.GPA())
                .dateCreated(dto.dateCreated())
                .build();
    }

}
