package com.tmoreno.mooc.backoffice.student.queries;

import com.tmoreno.mooc.backoffice.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.shared.query.VoidQueryParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindStudentsQueryTest {

    @Mock
    private StudentRepository repository;

    private FindStudentsQuery query;

    @BeforeEach
    void setUp() {
        query = new FindStudentsQuery(repository);
    }

    @Test
    public void given_no_students_when_find_then_return_empty_response() {
        FindStudentsQueryResponse response = query.execute(new VoidQueryParams());

        assertThat(response.getStudents(), is(empty()));
    }

    @Test
    public void given_there_are_three_students_when_find_then_return_three_students() {
        Student student1 = StudentMother.random();
        Student student2 = StudentMother.random();
        Student student3 = StudentMother.random();

        when(repository.findAll()).thenReturn(List.of(student1, student2, student3));

        FindStudentsQueryResponse response = query.execute(new VoidQueryParams());

        assertThat(response.getStudents().size(), is(3));
        assertStudent(response.getStudents().get(0), student1);
        assertStudent(response.getStudents().get(1), student2);
        assertStudent(response.getStudents().get(2), student3);
    }

    private void assertStudent(FindStudentQueryResponse response, Student student) {
        assertThat(response.getName(), is(student.getName().getValue()));
        assertThat(response.getEmail(), is(student.getEmail().getValue()));
        assertThat(response.getCourses(), is(student.getCourses()));
        assertThat(response.getReviews(), is(student.getReviews()));
    }
}
