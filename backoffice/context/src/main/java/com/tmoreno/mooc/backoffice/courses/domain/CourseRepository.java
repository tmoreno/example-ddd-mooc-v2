package com.tmoreno.mooc.backoffice.courses.domain;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    void save(Course course);

    List<Course> searchAll();

    Optional<Course> search(CourseId id);
}
