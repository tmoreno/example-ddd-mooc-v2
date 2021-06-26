package com.tmoreno.mooc.backoffice.teacher.domain;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.domain.events.TeacherCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.events.TeacherEmailChangedDomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.events.TeacherNameChangedDomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherCourseNotFoundException;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptySet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeacherTest {
    @Test
    public void should_create_a_teacher_and_record_an_event() {
        TeacherId teacherId = TeacherIdMother.random();
        PersonName name = PersonNameMother.random();
        Email email = EmailMother.random();

        Teacher teacher = Teacher.create(teacherId, name, email);

        assertThat(teacher.getId(), is(teacherId));
        assertThat(teacher.getName(), is(name));
        assertThat(teacher.getEmail(), is(email));
        assertThat(teacher.getCourses(), is(emptySet()));

        List<DomainEvent> domainEvents = teacher.pullEvents();
        assertThat(domainEvents.size(), is(1));

        TeacherCreatedDomainEvent event = (TeacherCreatedDomainEvent) domainEvents.get(0);

        assertThat(teacherId.getValue(), is(event.getTeacherId()));
        assertThat(name.getValue(), is(event.getName()));
        assertThat(email.getValue(), is(event.getEmail()));
    }

    @Test
    public void given_a_teacher_when_change_the_name_then_name_is_changed_and_an_event_is_recorded() {
        Teacher teacher = TeacherMother.random();
        PersonName name = PersonNameMother.random();

        teacher.changeName(name);

        assertThat(teacher.getName(), is(name));

        List<DomainEvent> domainEvents = teacher.pullEvents();
        assertThat(domainEvents.size(), is(1));

        TeacherNameChangedDomainEvent event = (TeacherNameChangedDomainEvent) domainEvents.get(0);

        assertThat(name.getValue(), is(event.getName()));
    }

    @Test
    public void given_a_teacher_when_change_the_email_then_email_is_changed_and_an_event_is_recorded() {
        Teacher teacher = TeacherMother.random();
        Email email = EmailMother.random();

        teacher.changeEmail(email);

        assertThat(teacher.getEmail(), is(email));

        List<DomainEvent> domainEvents = teacher.pullEvents();
        assertThat(domainEvents.size(), is(1));

        TeacherEmailChangedDomainEvent event = (TeacherEmailChangedDomainEvent) domainEvents.get(0);

        assertThat(email.getValue(), is(event.getEmail()));
    }

    @Test
    public void given_a_teacher_when_add_a_course_then_course_is_added() {
        Teacher teacher = TeacherMother.random();
        CourseId courseId = CourseIdMother.random();

        teacher.addCourse(courseId);

        assertTrue(teacher.getCourses().contains(courseId));
    }

    @Test
    public void given_a_teacher_with_courses_when_delete_a_course_then_course_is_deleted() {
        CourseId courseId = CourseIdMother.random();
        Teacher teacher = TeacherMother.randomWithCourse(courseId);

        teacher.deleteCourse(courseId);

        assertTrue(teacher.getCourses().isEmpty());
    }

    @Test
    public void given_a_teacher_with_courses_when_delete_a_not_existing_course_then_throws_an_exception() {
        Assertions.assertThrows(TeacherCourseNotFoundException.class, () -> {
            Teacher teacher = TeacherMother.randomWithCourse(CourseIdMother.random());

            teacher.deleteCourse(CourseIdMother.random());
        });
    }
}
