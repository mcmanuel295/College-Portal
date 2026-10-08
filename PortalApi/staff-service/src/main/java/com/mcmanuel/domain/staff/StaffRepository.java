package com.mcmanuel.domain.staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface StaffRepository extends JpaRepository<Staff, UUID> {
    Optional<Staff> findByStaffNumber(String staffNumber);
}
