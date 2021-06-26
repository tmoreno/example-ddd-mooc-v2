package com.tmoreno.mooc.backoffice.student.domain;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.mothers.StudentIdMother;
import com.tmoreno.mooc.backoffice.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentCourseNotFoundException;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentReviewNotFoundException;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.mothers.EmailMother;
import com.tmoreno.mooc.shared.mothers.PersonNameMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptySet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTest {

    @Test
    public void should_create_a_student() {
        StudentId id = StudentIdMother.random();
        PersonName name = PersonNameMother.random();
        Email email = EmailMother.random();

        Student student = new Student(id, name, email);

        assertThat(student.getId(), is(id));
        assertThat(student.getName(), is(name));
        assertThat(student.getEmail(), is(email));
        assertThat(student.getCourses(), is(emptySet()));
    }

    @Test
    public void given_a_student_when_change_the_name_then_name_is_changed() {
        Student student = StudentMother.random();
        PersonName name = PersonNameMother.random();

        student.changeName(name);

        assertThat(student.getName(), is(name));
    }

    @Test
    public void given_a_student_when_change_the_email_then_email_is_changed() {
        Student student = StudentMother.random();
        Email email = EmailMother.random();

        student.changeEmail(email);

        assertThat(student.getEmail(), is(email));
    }

    @Test
    public void given_a_student_when_add_a_course_then_course_is_added() {
        Student student = StudentMother.random();
        CourseId courseId = CourseIdMother.random();

        student.addCourse(courseId);

        assertTrue(student.getCourses().contains(courseId));
    }

    @Test
    public void given_a_student_with_courses_when_delete_a_course_then_course_is_deleted() {
        CourseId courseId = CourseIdMother.random();
        Student student = StudentMother.randomWithCourse(courseId);

        student.deleteCourse(courseId);

        assertTrue(student.getCourses().isEmpty());
    }

    @Test
    public void given_a_student_with_courses_when_delete_a_not_existing_course_then_throws_an_exception() {
        Assertions.assertThrows(StudentCourseNotFoundException.class, () -> {
            Student student = StudentMother.randomWithCourse(CourseIdMother.random());

            student.deleteCourse(CourseIdMother.random());
        });
    }

    @Test
    public void given_a_student_when_add_a_review_then_review_is_added() {
        Student student = StudentMother.random();
        CourseId courseId = CourseIdMother.random();
        ReviewId reviewId = ReviewIdMother.random();

        student.addReview(courseId, reviewId);

        assertThat(student.getReviews().get(courseId), is(reviewId));
    }

    @Test
    public void given_a_student_with_reviews_when_delete_a_review_then_review_is_deleted() {
        CourseId courseId = CourseIdMother.random();
        ReviewId reviewId = ReviewIdMother.random();
        Student student = StudentMother.randomWithReview(courseId, reviewId);

        student.deleteReview(courseId, reviewId);

        assertTrue(student.getReviews().isEmpty());
    }

    @Test
    public void given_a_student_with_reviews_when_delete_a_not_existing_review_then_throws_an_exception() {
        Assertions.assertThrows(StudentReviewNotFoundException.class, () -> {
            Student student = StudentMother.randomWithReview(CourseIdMother.random(), ReviewIdMother.random());

            student.deleteReview(CourseIdMother.random(), ReviewIdMother.random());
        });
    }
}
