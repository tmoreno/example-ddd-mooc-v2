package com.tmoreno.mooc.backoffice.course.infrastructure;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CourseJpaRepository implements CourseRepository {

    private final CourseDaoJpaRepository daoRepository;

    public CourseJpaRepository(CourseDaoJpaRepository daoRepository) {
        this.daoRepository = daoRepository;
    }

    @Override
    public void save(Course course) {
        CourseJpaDto courseJpaDto = CourseJpaDto.fromCourse(course);

        daoRepository.save(courseJpaDto);
    }

    @Override
    public List<Course> findAll() {
        return daoRepository
                .findAll()
                .stream()
                .map(CourseJpaDto::toCourse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Course> find(CourseId id) {
        return daoRepository
                .findById(id.getValue())
                .map(CourseJpaDto::toCourse);
    }

    @Override
    public boolean exists(CourseId id, CourseTitle title) {
        return daoRepository.existsById(id.getValue())
                || daoRepository.existsByTitle(title.getValue());
    }
}
