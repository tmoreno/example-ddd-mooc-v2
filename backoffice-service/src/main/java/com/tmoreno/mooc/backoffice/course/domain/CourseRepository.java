package com.tmoreno.mooc.backoffice.course.domain;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    void save(Course course);

    List<Course> findAll();

    Optional<Course> find(CourseId id);

    boolean exists(CourseId id, CourseTitle title);
}
