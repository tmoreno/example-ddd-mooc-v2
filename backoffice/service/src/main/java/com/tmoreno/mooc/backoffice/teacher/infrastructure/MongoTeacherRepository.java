package com.tmoreno.mooc.backoffice.teacher.infrastructure;

import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.domain.Email;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public final class MongoTeacherRepository implements TeacherRepository {
    @Override
    public void save(Teacher teacher) {

    }

    @Override
    public List<Teacher> findAll() {
        return null;
    }

    @Override
    public Optional<Teacher> find(TeacherId id) {
        return Optional.empty();
    }

    @Override
    public boolean exists(TeacherId id, Email email) {
        return false;
    }
}
