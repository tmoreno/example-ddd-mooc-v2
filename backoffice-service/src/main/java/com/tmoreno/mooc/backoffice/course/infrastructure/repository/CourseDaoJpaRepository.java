package com.tmoreno.mooc.backoffice.course.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDaoJpaRepository extends JpaRepository<CourseJpaDto, String> {

    boolean existsByTitle(String title);

}
