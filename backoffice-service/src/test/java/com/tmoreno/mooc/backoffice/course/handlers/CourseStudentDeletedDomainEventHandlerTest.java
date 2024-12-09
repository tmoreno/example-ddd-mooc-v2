package com.tmoreno.mooc.backoffice.course.handlers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseStudentDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.student.domain.Student;
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
    private CourseRepository repository;

    private CourseStudentDeletedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new CourseStudentDeletedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_student_have_been_deleted_from_a_course_when_handle_the_event_then_course_has_not_the_student() {
        Student student = StudentMother.random();
        Course course = CourseMother.randomInPublishStateWithStudent(student);

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        handler.handle(new CourseStudentDeletedDomainEvent(course.getId(), student.getId()));

        assertThat(course.getStudents(), is(emptySet()));

        verify(repository).save(course);
    }
}
