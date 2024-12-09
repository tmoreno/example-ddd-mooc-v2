package com.tmoreno.mooc.backoffice.student.infrastructure.repository;

import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.shared.domain.Email;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StudentJpaRepository implements StudentRepository {

    private final StudentDaoJpaRepository daoRepository;

    public StudentJpaRepository(StudentDaoJpaRepository daoRepository) {
        this.daoRepository = daoRepository;
    }

    @Override
    public void save(Student student) {
        StudentJpaDto studentJpaDto = StudentJpaDto.fromStudent(student);

        daoRepository.save(studentJpaDto);
    }

    @Override
    public List<Student> findAll() {
        return daoRepository
                .findAll()
                .stream()
                .map(StudentJpaDto::toStudent)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Student> find(StudentId id) {
        return daoRepository
                .findById(id.getValue())
                .map(StudentJpaDto::toStudent);
    }

    @Override
    public boolean exists(StudentId id, Email email) {
        return daoRepository.existsById(id.getValue())
                || daoRepository.existsByEmail(email.getValue());
    }
}
