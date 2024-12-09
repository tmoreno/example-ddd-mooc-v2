package com.tmoreno.mooc.backoffice.course.commands;

import com.tmoreno.mooc.backoffice.course.commands.deleteTeacher.CourseDeleteTeacherCommand;
import com.tmoreno.mooc.backoffice.course.commands.deleteTeacher.CourseDeleteTeacherCommandParams;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.shared.fakes.FakeEventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseDeleteTeacherCommandTest {

    @Mock
    private CourseRepository repository;

    private FakeEventBus eventBus;

    private CourseDeleteTeacherCommand command;

    @BeforeEach
    public void setUp() {
        eventBus = new FakeEventBus();
        command = new CourseDeleteTeacherCommand(repository, eventBus);
    }

    @Test
    public void given_a_course_with_teacher_when_delete_teacher_then_teacher_is_deleted_and_persisted_and_an_event_is_published() {
        Teacher teacher = TeacherMother.random();
        Course course = CourseMother.randomInDraftStateWithTeacher(teacher);

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        CourseDeleteTeacherCommandParams params = new CourseDeleteTeacherCommandParams();
        params.setCourseId(course.getId().getValue());
        params.setTeacherId(teacher.getId().getValue());

        command.execute(params);

        assertThat(course.getTeachers(), is(empty()));

        verify(repository).save(course);

        assertThat(eventBus.getEvents().size(), is(1));
    }

    @Test
    public void given_a_not_existing_course_when_delete_teacher_then_throws_exception() {
        assertThrows(CourseNotFoundException.class, () -> {
            CourseDeleteTeacherCommandParams params = new CourseDeleteTeacherCommandParams();
            params.setCourseId(CourseIdMother.random().getValue());
            params.setTeacherId(TeacherIdMother.random().getValue());

            command.execute(params);
        });
    }
}
