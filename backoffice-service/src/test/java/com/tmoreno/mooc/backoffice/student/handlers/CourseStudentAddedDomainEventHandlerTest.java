package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseStudentAddedDomainEvent;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseStudentAddedDomainEventHandlerTest {

    @Mock
    private StudentRepository repository;

    private CourseStudentAddedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new CourseStudentAddedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_course_have_added_to_a_student_when_handle_the_event_then_student_has_the_course_added() {
        CourseId courseId = CourseIdMother.random();
        Student student = StudentMother.random();

        when(repository.find(student.getId())).thenReturn(Optional.of(student));

        handler.handle(new CourseStudentAddedDomainEvent(courseId, student.getId()));

        assertThat(student.getCourses().size(), is(1));
        assertThat(student.getCourses().contains(courseId), is(true));

        verify(repository).save(student);
    }
}
