package com.mcmanuel.domain.staff.non_teaching;

import com.mcmanuel.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record NonTeachingDto (
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

        @Column(nullable = false)
        List<Role> roles,

        @Column(nullable = false)
        LocalDateTime dateCreated,

        String imageUrl

) {
}
