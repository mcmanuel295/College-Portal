package com.mcmanuel.domain.staff.lecturer;

import com.mcmanuel.domain.staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {
    Optional<Staff> findByStaffId(String staffId);

    Optional<Staff>  findByEmail(String email);
}
