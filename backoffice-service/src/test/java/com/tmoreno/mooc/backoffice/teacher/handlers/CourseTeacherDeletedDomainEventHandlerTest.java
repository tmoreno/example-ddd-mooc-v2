package com.tmoreno.mooc.backoffice.teacher.handlers;

import com.tmoreno.mooc.backoffice.course.domain.events.CourseTeacherDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.domain.CourseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseTeacherDeletedDomainEventHandlerTest {

    @Mock
    private TeacherRepository repository;

    private CourseTeacherDeletedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new CourseTeacherDeletedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_teacher_is_deleted_from_a_course_when_handle_the_event_then_teacher_not_has_the_course() {
        CourseId courseId = CourseIdMother.random();
        Teacher teacher = TeacherMother.randomWithCourse(courseId);

        when(repository.find(teacher.getId())).thenReturn(Optional.of(teacher));

        handler.handle(new CourseTeacherDeletedDomainEvent(courseId, teacher.getId()));

        assertThat(teacher.getCourses(), is(empty()));

        verify(repository).save(teacher);
    }
}
