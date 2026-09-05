package com.mcmanuel.domain.result;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, UUID> {
    Result findByMatriculationNumberAndSemester(String matriculationNumber, String semester);
}
