package com.mcmanuel.domain.student;

import com.mcmanuel.enums.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

interface StudentRepository extends JpaRepository<Student,String> {
    Optional<Student> findByMatriculationNumber(String MatricNumber);


    @Query("SELECT matriculationNumber m FROM Student s WHERE s.department=:department")
    List<String> getMatriculationNumberList(Department department);

    @Query("SELECT DISTINCT department FROM Student")
    List<Department> getAllDepartments();

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.GPA= :value WHERE s.matriculationNumber= :matricNumber")
    void saveGPA(@Param("matricNumber") String matricNumber, @Param("value") double value);

    List<Student> findAllByDepartment(String department);

    Optional<Student> findByEmail(String email);
}
