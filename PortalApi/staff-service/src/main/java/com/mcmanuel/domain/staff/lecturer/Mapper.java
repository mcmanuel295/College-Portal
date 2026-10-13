package com.mcmanuel.domain.lecturer;

class Mapper {
    public static Lecturer toLecturer(LecturerDto dto){
        return Lecturer.builder()
                .firstname(dto.firstname())
                .lastname(dto.lastname())
                .staffNumber(dto.staffId())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .department(dto.department())
                .roles(dto.role())
                .courses(dto.courses())
                .dateCreated(dto.dateCreated())
                .imageUrl(dto.imageUrl())
                .build();
    }


    public static LecturerDto toDto(Lecturer lecturer){
        return LecturerDto.builder()
                .firstname(lecturer.getFirstname())
                .lastname(lecturer.getLastname())
                .staffId(lecturer.getStaffNumber())
                .email(lecturer.getEmail())
                .phoneNumber(lecturer.getPhoneNumber())
                .department(lecturer.getDepartment())
                .role(lecturer.getRoles())
                .courses(lecturer.getCourses())
                .dateCreated(lecturer.getDateCreated())
                .imageUrl(lecturer.getImageUrl())
                .build();
    }
}
