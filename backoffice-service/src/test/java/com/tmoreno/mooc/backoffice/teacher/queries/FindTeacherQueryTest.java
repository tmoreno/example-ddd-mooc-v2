package com.tmoreno.mooc.backoffice.teacher.queries;

import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.Identifier;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.exceptions.teacher.TeacherNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindTeacherQueryTest {

    @Mock
    private TeacherRepository repository;

    private FindTeacherQuery query;

    @BeforeEach
    public void setUp() {
        query = new FindTeacherQuery(repository);
    }

    @Test
    public void given_a_existing_teacher_id_when_find_then_return_the_teacher() {
        CourseId courseId = CourseIdMother.random();
        Teacher teacher = TeacherMother.randomWithCourse(courseId);

        when(repository.find(teacher.getId())).thenReturn(Optional.of(teacher));

        FindTeacherQueryParams params = new FindTeacherQueryParams();
        params.setTeacherId(teacher.getId().getValue());

        FindTeacherQueryResponse response = query.execute(params);

        assertThat(response.getId(), is(teacher.getId().getValue()));
        assertThat(response.getName(), is(teacher.getName().getValue()));
        assertThat(response.getEmail(), is(teacher.getEmail().getValue()));
        assertThat(response.getCourses(), is(teacher.getCourses().stream().map(Identifier::getValue).collect(Collectors.toSet())));
    }

    @Test
    public void given_a_not_existing_teacher_id_when_find_then_throws_an_exception() {
        Assertions.assertThrows(TeacherNotFoundException.class, () -> {
            TeacherId teacherId = TeacherIdMother.random();

            when(repository.find(teacherId)).thenReturn(Optional.empty());

            FindTeacherQueryParams params = new FindTeacherQueryParams();
            params.setTeacherId(teacherId.getValue());

            query.execute(params);
        });
    }
}
