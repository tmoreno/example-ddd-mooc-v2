package com.tmoreno.mooc.backoffice.teachers.queries;

import com.tmoreno.mooc.backoffice.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teachers.domain.Teacher;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teachers.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.backoffice.teachers.queries.find.FindTeacherQuery;
import com.tmoreno.mooc.backoffice.teachers.queries.find.FindTeacherQueryParams;
import com.tmoreno.mooc.backoffice.teachers.queries.find.FindTeacherQueryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        TeacherId teacherId = TeacherIdMother.random();
        Teacher teacher = TeacherMother.random();

        when(repository.find(teacherId)).thenReturn(Optional.of(teacher));

        FindTeacherQueryParams params = new FindTeacherQueryParams();
        params.setTeacherId(teacherId.getValue());

        FindTeacherQueryResponse response = query.execute(params);

        assertThat(response.getName(), is(teacher.getName().getValue()));
        assertThat(response.getEmail(), is(teacher.getEmail().getValue()));
        assertThat(response.getCourses(), is(teacher.getCourses()));
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
