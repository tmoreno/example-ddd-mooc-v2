package com.tmoreno.mooc.backoffice.student.domain;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.review.ReviewId;
import com.tmoreno.mooc.backoffice.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.mothers.StudentIdMother;
import com.tmoreno.mooc.backoffice.mothers.StudentMother;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentCourseAddedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentCourseDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentEmailChangedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentNameChangedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentReviewAddedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.events.StudentReviewDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentCourseNotFoundException;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentReviewNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptySet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTest {

    @Test
    public void should_create_a_student_and_record_an_event() {
        StudentId id = StudentIdMother.random();
        PersonName name = PersonNameMother.random();
        Email email = EmailMother.random();

        Student student = Student.create(id, name, email);

        assertThat(student.getId(), is(id));
        assertThat(student.getName(), is(name));
        assertThat(student.getEmail(), is(email));
        assertThat(student.getCourses(), is(emptySet()));

        List<DomainEvent> domainEvents = student.pullEvents();
        assertThat(domainEvents.size(), is(1));

        StudentCreatedDomainEvent event = (StudentCreatedDomainEvent) domainEvents.get(0);

        assertThat(id.getValue(), is(event.getAggregateId()));
        assertThat(name, is(event.getName()));
        assertThat(email, is(event.getEmail()));
    }

    @Test
    public void given_a_student_when_change_the_name_then_name_is_changed_and_an_event_is_recorded() {
        Student student = StudentMother.random();
        PersonName name = PersonNameMother.random();

        student.changeName(name);

        assertThat(student.getName(), is(name));

        List<DomainEvent> domainEvents = student.pullEvents();
        assertThat(domainEvents.size(), is(1));

        StudentNameChangedDomainEvent event = (StudentNameChangedDomainEvent) domainEvents.get(0);

        assertThat(name, is(event.getName()));
    }

    @Test
    public void given_a_student_when_change_the_email_then_email_is_changed_and_an_event_is_recorded() {
        Student student = StudentMother.random();
        Email email = EmailMother.random();

        student.changeEmail(email);

        assertThat(student.getEmail(), is(email));

        List<DomainEvent> domainEvents = student.pullEvents();
        assertThat(domainEvents.size(), is(1));

        StudentEmailChangedDomainEvent event = (StudentEmailChangedDomainEvent) domainEvents.get(0);

        assertThat(email, is(event.getEmail()));
    }

    @Test
    public void given_a_student_when_add_a_course_then_course_is_added_and_an_event_is_recorded() {
        Student student = StudentMother.random();
        CourseId courseId = CourseIdMother.random();

        student.addCourse(courseId);

        assertTrue(student.getCourses().contains(courseId));

        List<DomainEvent> domainEvents = student.pullEvents();
        assertThat(domainEvents.size(), is(1));

        StudentCourseAddedDomainEvent event = (StudentCourseAddedDomainEvent) domainEvents.get(0);

        assertThat(courseId, is(event.getCourseId()));
    }

    @Test
    public void given_a_student_with_courses_when_delete_a_course_then_course_is_deleted_and_an_event_is_recorded() {
        CourseId courseId = CourseIdMother.random();
        Student student = StudentMother.randomWithCourse(courseId);

        student.deleteCourse(courseId);

        assertTrue(student.getCourses().isEmpty());

        List<DomainEvent> domainEvents = student.pullEvents();
        assertThat(domainEvents.size(), is(1));

        StudentCourseDeletedDomainEvent event = (StudentCourseDeletedDomainEvent) domainEvents.get(0);

        assertThat(courseId, is(event.getCourseId()));
    }

    @Test
    public void given_a_student_with_courses_when_delete_a_not_existing_course_then_throws_an_exception() {
        Assertions.assertThrows(StudentCourseNotFoundException.class, () -> {
            Student student = StudentMother.randomWithCourse(CourseIdMother.random());

            student.deleteCourse(CourseIdMother.random());
        });
    }

    @Test
    public void given_a_student_when_add_a_review_then_review_is_added_and_an_event_is_recorded() {
        Student student = StudentMother.random();
        ReviewId reviewId = ReviewIdMother.random();

        student.addReview(reviewId);

        assertTrue(student.getReviews().contains(reviewId));

        List<DomainEvent> domainEvents = student.pullEvents();
        assertThat(domainEvents.size(), is(1));

        StudentReviewAddedDomainEvent event = (StudentReviewAddedDomainEvent) domainEvents.get(0);

        assertThat(reviewId, is(event.getReviewId()));
    }

    @Test
    public void given_a_student_with_reviews_when_delete_a_review_then_review_is_deleted_and_an_event_is_recorded() {
        ReviewId reviewId = ReviewIdMother.random();
        Student student = StudentMother.randomWithReview(reviewId);

        student.deleteReview(reviewId);

        assertTrue(student.getReviews().isEmpty());

        List<DomainEvent> domainEvents = student.pullEvents();
        assertThat(domainEvents.size(), is(1));

        StudentReviewDeletedDomainEvent event = (StudentReviewDeletedDomainEvent) domainEvents.get(0);

        assertThat(reviewId, is(event.getReviewId()));
    }

    @Test
    public void given_a_student_with_reviews_when_delete_a_not_existing_review_then_throws_an_exception() {
        Assertions.assertThrows(StudentReviewNotFoundException.class, () -> {
            Student student = StudentMother.randomWithReview(ReviewIdMother.random());

            student.deleteReview(ReviewIdMother.random());
        });
    }
}
