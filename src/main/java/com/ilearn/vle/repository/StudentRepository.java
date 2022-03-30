package com.ilearn.vle.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ilearn.vle.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEnrolment(String enrolment);
    //@Query("SELECT s FROM Student s LEFT JOIN s.project p WHERE s.project.id = :id")
    List<Student> findByProjectId(Long id);
}
