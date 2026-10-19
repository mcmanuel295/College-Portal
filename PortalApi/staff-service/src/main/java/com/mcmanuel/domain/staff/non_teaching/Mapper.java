package com.mcmanuel.domain.staff.non_teaching;


class Mapper {
    public static NonTeaching toLecturer(NonTeachingDto dto){
        return NonTeaching.builder()
                .firstname(dto.firstname())
                .lastname(dto.lastname())
                .staffId(dto.staffId())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .roles(dto.roles())
                .dateCreated(dto.dateCreated())
                .imageUrl(dto.imageUrl())
                .build();
    }


    public static NonTeachingDto toDto(NonTeaching lecturer){
        return NonTeachingDto.builder()
                .firstname(lecturer.getFirstname())
                .lastname(lecturer.getLastname())
                .staffId(lecturer.getStaffId())
                .email(lecturer.getEmail())
                .phoneNumber(lecturer.getPhoneNumber())
                .roles(lecturer.getRoles())
                .dateCreated(lecturer.getDateCreated())
                .imageUrl(lecturer.getImageUrl())
                .build();
    }
}
