package com.tmoreno.mooc.backoffice.teachers.handlers;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.courses.domain.events.CourseTeacherAddedDomainEvent;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teachers.domain.Teacher;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherRepository;
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
public class CourseTeacherAddedDomainEventHandlerTest {

    @Mock
    private TeacherRepository repository;

    private CourseTeacherAddedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new CourseTeacherAddedDomainEventHandler(repository);
    }

    @Test
    void given_that_a_teacher_is_added_to_a_course_when_handle_the_event_then_teacher_has_the_course_added() {
        Teacher teacher = TeacherMother.random();
        CourseId courseId = CourseIdMother.random();

        when(repository.find(teacher.getId())).thenReturn(Optional.of(teacher));

        handler.handle(new CourseTeacherAddedDomainEvent(courseId, teacher.getId()));

        assertThat(teacher.getCourses().size(), is(1));
        assertThat(teacher.getCourses().contains(courseId), is(true));

        verify(repository).save(teacher);
    }
}
