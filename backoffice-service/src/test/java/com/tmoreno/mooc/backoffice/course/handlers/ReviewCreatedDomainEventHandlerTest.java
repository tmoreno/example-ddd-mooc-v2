package com.tmoreno.mooc.backoffice.course.handlers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.ReviewRatingMother;
import com.tmoreno.mooc.backoffice.common.mothers.ReviewTextMother;
import com.tmoreno.mooc.backoffice.common.mothers.StudentIdMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.events.ReviewCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.mothers.CreatedOnMother;
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
public class ReviewCreatedDomainEventHandlerTest {

    @Mock
    private CourseRepository repository;

    private ReviewCreatedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new ReviewCreatedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_student_have_added_a_review_when_handle_the_event_then_course_has_the_review_added() {
        Course course = CourseMother.randomInPublishState();
        StudentId studentId = StudentIdMother.random();
        ReviewId reviewId = ReviewIdMother.random();

        when(repository.find(course.getId())).thenReturn(Optional.of(course));

        handler.handle(new ReviewCreatedDomainEvent(
                reviewId,
                course.getId(),
                studentId,
                ReviewRatingMother.random(),
                ReviewTextMother.random(),
                CreatedOnMother.random()
        ));

        assertThat(course.getReviews().size(), is(1));
        assertThat(course.getReviews().get(studentId), is(reviewId));

        verify(repository).save(course);
    }
}
