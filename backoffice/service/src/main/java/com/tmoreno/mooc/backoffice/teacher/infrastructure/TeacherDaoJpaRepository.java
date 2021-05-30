package com.tmoreno.mooc.backoffice.teacher.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherDaoJpaRepository extends JpaRepository<TeacherJpaDto, String> {

    boolean existsByEmail(String email);

}
