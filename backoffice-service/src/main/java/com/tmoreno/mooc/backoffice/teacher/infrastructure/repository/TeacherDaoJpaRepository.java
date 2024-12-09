package com.tmoreno.mooc.backoffice.teacher.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherDaoJpaRepository extends JpaRepository<TeacherJpaDto, String> {

    boolean existsByEmail(String email);

}
