package com.mcmanuel.domain.lecturer;

import com.mcmanuel.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record LecturerDto(
        @Column(name = "firstname", nullable = false)
        String firstname,

        @Column(name = "lastname", nullable = false)
        String lastname,

        @Column(nullable = false)
        String fullName,

        @Column(unique = true, nullable = false, length = 9)
        String staffId,

        @Email
        @Column(name = "email", unique = true, nullable = false)
        String email,

        @Column(nullable = false)
        String phoneNumber,

        @Enumerated(EnumType.STRING)
        @Column(unique = true, nullable = false)
        Department department,

        Set<String> courses,

        @Column(nullable = false)
        Role role,

        @Column(nullable = false)
        LocalDateTime dateCreated,

        String imageUrl

) {
}
