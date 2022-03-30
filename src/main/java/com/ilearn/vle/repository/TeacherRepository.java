package com.ilearn.vle.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ilearn.vle.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEnrolment(String enrolment);
    @Query("SELECT t FROM Teacher t LEFT JOIN t.project p WHERE t.id = :id AND p.teacher.id = :id")
    Optional<Teacher> findByProject(Long id);
    @Query("SELECT t FROM Teacher t LEFT JOIN t.project p WHERE t.project.id = :id")
    Optional<Teacher> findByProjectId(Long id);
}
