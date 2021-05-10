package com.tmoreno.mooc.backoffice.student.queries;

import com.tmoreno.mooc.backoffice.mothers.StudentIdMother;
import com.tmoreno.mooc.backoffice.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentNotFoundException;
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
public class FindStudentQueryTest {

    @Mock
    private StudentRepository repository;

    private FindStudentQuery query;

    @BeforeEach
    public void setUp() {
        query = new FindStudentQuery(repository);
    }

    @Test
    public void given_a_existing_student_id_when_find_then_return_the_student() {
        StudentId studentId = StudentIdMother.random();
        Student student = StudentMother.random();

        when(repository.find(studentId)).thenReturn(Optional.of(student));

        FindStudentQueryParams params = new FindStudentQueryParams();
        params.setStudentId(studentId.getValue());

        FindStudentQueryResponse response = query.execute(params);

        assertThat(response.getName(), is(student.getName().getValue()));
        assertThat(response.getEmail(), is(student.getEmail().getValue()));
        assertThat(response.getCourses(), is(student.getCourses()));
        assertThat(response.getReviews(), is(student.getReviews()));
    }

    @Test
    public void given_a_not_existing_student_id_when_find_then_throws_an_exception() {
        Assertions.assertThrows(StudentNotFoundException.class, () -> {
            StudentId studentId = StudentIdMother.random();

            when(repository.find(studentId)).thenReturn(Optional.empty());

            FindStudentQueryParams params = new FindStudentQueryParams();
            params.setStudentId(studentId.getValue());

            query.execute(params);
        });
    }
}
