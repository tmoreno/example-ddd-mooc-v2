package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.addTeacher.CourseAddTeacherCommand;
import com.tmoreno.mooc.backoffice.course.commands.addTeacher.CourseAddTeacherCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.exceptions.teacher.TeacherNotFoundException;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseAddTeacherCommandTest {

    @Mock
    private CourseRepository repository;

    @Mock
    private TeacherRepository teacherRepository;

    private FakeEventBus eventBus;

    private CourseAddTeacherCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseAddTeacherCommand(repository, teacherRepository, eventBus);
    }

    @Test
    public void given_a_not_added_teacher_when_add_teacher_then_teacher_is_added_and_persisted_and_an_event_is_published() {
        Course course = CourseMother.randomInDraftState();
        TeacherId teacherId = TeacherIdMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));
        when(teacherRepository.exists(teacherId)).thenReturn(true);

        CourseAddTeacherCommandParams params = new CourseAddTeacherCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setTeacherId(teacherId.getValue());

        command.execute(params);

        assertThat(course.getTeachers().size(), is(1));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_add_teacher_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            TeacherId teacherId = TeacherIdMother.random();
            when(teacherRepository.exists(teacherId)).thenReturn(true);

            CourseAddTeacherCommandParams params = new CourseAddTeacherCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setTeacherId(teacherId.getValue());

            command.execute(params);
        });
    }

    @Test
    public void given_a_not_existing_teacher_when_add_teacher_then_throws_exception() {
        assertThrows(TeacherNotFoundException.class, () -> {
            CourseAddTeacherCommandParams params = new CourseAddTeacherCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setTeacherId(TeacherIdMother.random().getValue());

            command.execute(params);
        });
    }
}
