package com.ilearn.vle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ilearn.vle.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {}
