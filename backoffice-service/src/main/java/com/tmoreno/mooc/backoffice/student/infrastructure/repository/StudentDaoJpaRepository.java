package com.tmoreno.mooc.backoffice.student.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDaoJpaRepository extends JpaRepository<StudentJpaDto, String> {

    boolean existsByEmail(String email);

}
