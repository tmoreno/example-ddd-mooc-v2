package com.tmoreno.mooc.backoffice.teacher.domain;

import com.tmoreno.mooc.shared.domain.Email;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository {
    void save(Teacher teacher);

    List<Teacher> findAll();

    Optional<Teacher> find(TeacherId id);

    boolean exists(TeacherId id, Email email);
}
