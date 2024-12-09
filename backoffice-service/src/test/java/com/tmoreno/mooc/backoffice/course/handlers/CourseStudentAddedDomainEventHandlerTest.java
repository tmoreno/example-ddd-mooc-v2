package com.tmoreno.mooc.backoffice.course.handlers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseStudentAddedDomainEvent;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.StudentIdMother;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
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
    private CourseRepository repository;

    private CourseStudentAddedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new CourseStudentAddedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_student_have_added_to_a_course_when_handle_the_event_then_course_has_the_student_added() {
        StudentId studentId = StudentIdMother.random();
        Course course = CourseMother.randomInPublishState();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        handler.handle(new CourseStudentAddedDomainEvent(course.getId(), studentId));

        assertThat(course.getStudents().size(), is(1));
        assertThat(course.getStudents().contains(studentId), is(true));

        verify(repository).save(course);
    }
}
