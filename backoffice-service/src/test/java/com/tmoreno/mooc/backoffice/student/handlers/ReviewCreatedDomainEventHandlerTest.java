package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.ReviewRatingMother;
import com.tmoreno.mooc.backoffice.common.mothers.ReviewTextMother;
import com.tmoreno.mooc.backoffice.common.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.events.ReviewCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.shared.domain.CourseId;
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
    private StudentRepository repository;

    private ReviewCreatedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new ReviewCreatedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_student_have_added_a_course_review_when_handle_the_event_then_student_has_the_course_review_added() {
        Student student = StudentMother.random();
        CourseId courseId = CourseIdMother.random();
        ReviewId reviewId = ReviewIdMother.random();

        when(repository.find(student.getId())).thenReturn(Optional.of(student));

        handler.handle(new ReviewCreatedDomainEvent(
                reviewId,
                courseId,
                student.getId(),
                ReviewRatingMother.random(),
                ReviewTextMother.random(),
                CreatedOnMother.random()
        ));

        assertThat(student.getReviews().size(), is(1));
        assertThat(student.getReviews().get(courseId), is(reviewId));

        verify(repository).save(student);
    }
}
