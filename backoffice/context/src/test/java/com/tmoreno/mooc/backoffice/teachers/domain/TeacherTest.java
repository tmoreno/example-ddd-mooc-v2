package com.tmoreno.mooc.backoffice.teachers.domain;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.utils.EmailMother;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.utils.PersonNameMother;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.events.TeacherCourseAddedDomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.events.TeacherCourseDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.events.TeacherCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.events.TeacherEmailChangedDomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.events.TeacherNameChangedDomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.exceptions.TeacherCourseNotFoundException;
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
        TeacherId id = TeacherIdMother.random();
        PersonName name = PersonNameMother.random();
        Email email = EmailMother.random();

        Teacher teacher = Teacher.create(id, name, email);

        assertThat(teacher.getId(), is(id));
        assertThat(teacher.getName(), is(name));
        assertThat(teacher.getEmail(), is(email));
        assertThat(teacher.getCourses(), is(emptySet()));

        List<DomainEvent> domainEvents = teacher.pullEvents();
        assertThat(domainEvents.size(), is(1));

        TeacherCreatedDomainEvent event = (TeacherCreatedDomainEvent) domainEvents.get(0);

        assertThat(id.getValue(), is(event.getAggregateId()));
        assertThat(name, is(event.getName()));
        assertThat(email, is(event.getEmail()));
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

        assertThat(name, is(event.getName()));
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

        assertThat(email, is(event.getEmail()));
    }

    @Test
    public void given_a_teacher_when_add_a_course_then_course_is_added_and_an_event_is_recorded() {
        Teacher teacher = TeacherMother.random();
        CourseId courseId = CourseIdMother.random();

        teacher.addCourse(courseId);

        assertTrue(teacher.getCourses().contains(courseId));

        List<DomainEvent> domainEvents = teacher.pullEvents();
        assertThat(domainEvents.size(), is(1));

        TeacherCourseAddedDomainEvent event = (TeacherCourseAddedDomainEvent) domainEvents.get(0);

        assertThat(courseId, is(event.getCourseId()));
    }

    @Test
    public void given_a_teacher_with_courses_when_delete_a_course_then_course_is_deleted_and_an_event_is_recorded() {
        CourseId courseId = CourseIdMother.random();
        Teacher teacher = TeacherMother.randomWithCourse(courseId);

        teacher.deleteCourse(courseId);

        assertTrue(teacher.getCourses().isEmpty());

        List<DomainEvent> domainEvents = teacher.pullEvents();
        assertThat(domainEvents.size(), is(1));

        TeacherCourseDeletedDomainEvent event = (TeacherCourseDeletedDomainEvent) domainEvents.get(0);

        assertThat(courseId, is(event.getCourseId()));
    }

    @Test
    public void given_a_teacher_with_courses_when_delete_a_not_existing_course_then_throws_an_exception() {
        Assertions.assertThrows(TeacherCourseNotFoundException.class, () -> {
            Teacher teacher = TeacherMother.randomWithCourse(CourseIdMother.random());

            teacher.deleteCourse(CourseIdMother.random());
        });
    }
}
