package com.mcmanuel.domain.staff.non_teaching;

import com.mcmanuel.domain.staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NonTeachingRepository extends JpaRepository<NonTeaching, UUID> {
    Optional<Staff> findByStaffId(String staffNumber);
    Optional<Staff> findByEmail(String email);
}
