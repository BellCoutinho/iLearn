package com.ilearn.vle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ilearn.vle.domain.Class;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    Optional<Class> findByDiscipline(String discipline);
}
