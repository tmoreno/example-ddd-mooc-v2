package com.tmoreno.mooc.backoffice.teachers.domain;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository {
    void save(Teacher teacher);

    List<Teacher> findAll();

    Optional<Teacher> find(TeacherId id);
}
