package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.course.domain.events.CourseStudentDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.shared.domain.CourseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Collections.emptySet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseStudentDeletedDomainEventHandlerTest {

    @Mock
    private StudentRepository repository;

    private CourseStudentDeletedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new CourseStudentDeletedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_course_have_been_deleted_from_a_student_when_handle_the_event_then_student_has_not_the_course() {
        CourseId courseId = CourseIdMother.random();
        Student student = StudentMother.randomWithCourse(courseId);

        when(repository.find(student.getId())).thenReturn(Optional.of(student));

        handler.handle(new CourseStudentDeletedDomainEvent(courseId, student.getId()));

        assertThat(student.getCourses(), is(emptySet()));

        verify(repository).save(student);
    }
}
