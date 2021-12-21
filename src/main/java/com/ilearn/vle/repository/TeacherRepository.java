package com.ilearn.vle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ilearn.vle.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {}
