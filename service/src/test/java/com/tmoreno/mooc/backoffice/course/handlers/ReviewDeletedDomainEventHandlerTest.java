package com.tmoreno.mooc.backoffice.course.handlers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.mothers.StudentIdMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.events.ReviewDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewDeletedDomainEventHandlerTest {

    @Mock
    private CourseRepository repository;

    private ReviewDeletedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new ReviewDeletedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_student_have_deleted_a_review_when_handle_the_event_then_course_has_not_the_review_added() {
        StudentId studentId = StudentIdMother.random();
        ReviewId reviewId = ReviewIdMother.random();
        Course course = CourseMother.randomInPublishStateWithReview(studentId, reviewId);

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        handler.handle(new ReviewDeletedDomainEvent(
                reviewId,
                course.getId(),
                studentId
        ));

        assertThat(course.getReviews(), is(Collections.emptyMap()));

        verify(repository).save(course);
    }
}
