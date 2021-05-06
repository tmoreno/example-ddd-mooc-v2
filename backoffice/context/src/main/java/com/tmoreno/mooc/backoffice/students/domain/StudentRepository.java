package com.tmoreno.mooc.backoffice.students.domain;

import com.tmoreno.mooc.shared.domain.Email;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    void save(Student student);

    List<Student> findAll();

    Optional<Student> find(StudentId id);

    boolean exists(StudentId id, Email email);
}
