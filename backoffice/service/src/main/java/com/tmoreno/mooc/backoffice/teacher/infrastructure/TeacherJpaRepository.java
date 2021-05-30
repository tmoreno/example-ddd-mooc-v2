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
public class TeacherJpaRepository implements TeacherRepository {

    private final TeacherDaoJpaRepository daoRepository;

    public TeacherJpaRepository(TeacherDaoJpaRepository daoRepository) {
        this.daoRepository = daoRepository;
    }

    @Override
    public void save(Teacher teacher) {
        TeacherJpaDto teacherJpaDto = TeacherJpaDto.fromTeacher(teacher);

        daoRepository.save(teacherJpaDto);
    }

    @Override
    public List<Teacher> findAll() {
        return daoRepository
                .findAll()
                .stream()
                .map(TeacherJpaDto::toTeacher)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Teacher> find(TeacherId id) {
        return daoRepository
                .findById(id.getValue())
                .map(TeacherJpaDto::toTeacher);
    }

    @Override
    public boolean exists(TeacherId id, Email email) {
        return daoRepository.existsById(id.getValue())
                || daoRepository.existsByEmail(email.getValue());
    }
}
