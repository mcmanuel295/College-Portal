package com.mcmanuel.domain.lecturer;

import com.mcmanuel.domain.staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface LecturerRepository extends JpaRepository<Staff, UUID> {
    Optional<Staff> findByStaffNumber(String staffNumber);
}
