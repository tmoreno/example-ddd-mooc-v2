package com.tmoreno.mooc.backoffice.student.handlers;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.events.ReviewDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
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
    private StudentRepository repository;

    private ReviewDeletedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new ReviewDeletedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_student_have_deleted_a_course_review_when_handle_the_event_then_student_has_not_the_course_review_added() {
        CourseId courseId = CourseIdMother.random();
        ReviewId reviewId = ReviewIdMother.random();
        Student student = StudentMother.randomWithReview(courseId, reviewId);

        when(repository.find(student.getId())).thenReturn(Optional.of(student));

        handler.handle(new ReviewDeletedDomainEvent(
                reviewId,
                courseId,
                student.getId()
        ));

        assertThat(student.getReviews(), is(Collections.emptyMap()));

        verify(repository).save(student);
    }
}
