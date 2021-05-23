package com.tmoreno.mooc.backoffice.teacher.infrastructure;

import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.domain.Email;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TeacherMongoRepository implements TeacherRepository {

    private final TeacherDaoMongoRepository daoRepository;

    public TeacherMongoRepository(TeacherDaoMongoRepository daoRepository) {
        this.daoRepository = daoRepository;
    }

    @Override
    public void save(Teacher teacher) {
        TeacherMongoDto teacherMongo = TeacherMongoDto.fromTeacher(teacher);

        daoRepository.save(teacherMongo);
    }

    @Override
    public List<Teacher> findAll() {
        return daoRepository
                .findAll()
                .stream()
                .map(TeacherMongoDto::toTeacher)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Teacher> find(TeacherId id) {
        return daoRepository
                .findById(id.getValue())
                .map(TeacherMongoDto::toTeacher);
    }

    @Override
    public boolean exists(TeacherId id, Email email) {
        return daoRepository.existsById(id.getValue())
                || daoRepository.existsByEmail(email.getValue());
    }
}
