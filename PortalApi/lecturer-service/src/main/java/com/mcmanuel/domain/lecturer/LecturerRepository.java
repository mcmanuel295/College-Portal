package com.mcmanuel.domain.lecturer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface LecturerRepository extends JpaRepository<Lecturer, UUID> {
    Optional<Lecturer> findByStaffNumber(String staffNumber);
}
