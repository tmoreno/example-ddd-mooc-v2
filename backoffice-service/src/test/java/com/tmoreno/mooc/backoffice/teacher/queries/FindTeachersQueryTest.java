package com.tmoreno.mooc.backoffice.teacher.queries;

import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.domain.Identifier;
import com.tmoreno.mooc.shared.query.VoidQueryParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindTeachersQueryTest {

    @Mock
    private TeacherRepository repository;

    private FindTeachersQuery query;

    @BeforeEach
    void setUp() {
        query = new FindTeachersQuery(repository);
    }

    @Test
    public void given_no_teachers_when_find_then_return_empty_response() {
        FindTeachersQueryResponse response = query.execute(new VoidQueryParams());

        assertThat(response.getTeachers(), is(empty()));
    }

    @Test
    public void given_there_are_three_teachers_when_find_then_return_three_teachers() {
        Teacher teacher1 = TeacherMother.random();
        Teacher teacher2 = TeacherMother.randomWithCourse(CourseIdMother.random());
        Teacher teacher3 = TeacherMother.random();

        when(repository.findAll()).thenReturn(List.of(teacher1, teacher2, teacher3));

        FindTeachersQueryResponse response = query.execute(new VoidQueryParams());

        assertThat(response.getTeachers().size(), is(3));
        assertTeacher(response.getTeachers().get(0), teacher1);
        assertTeacher(response.getTeachers().get(1), teacher2);
        assertTeacher(response.getTeachers().get(2), teacher3);
    }

    private void assertTeacher(FindTeacherQueryResponse response, Teacher teacher) {
        assertThat(response.getId(), is(teacher.getId().getValue()));
        assertThat(response.getName(), is(teacher.getName().getValue()));
        assertThat(response.getEmail(), is(teacher.getEmail().value()));
        assertThat(response.getCourses(), is(teacher.getCourses().stream().map(Identifier::getValue).collect(Collectors.toSet())));
    }
}
