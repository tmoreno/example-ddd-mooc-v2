package com.tmoreno.mooc.backoffice.review.handlers;

import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.ReviewRatingMother;
import com.tmoreno.mooc.backoffice.common.mothers.ReviewTextMother;
import com.tmoreno.mooc.backoffice.common.mothers.StudentIdMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.backoffice.review.domain.events.ReviewCreatedDomainEvent;
import com.tmoreno.mooc.shared.mothers.CreatedOnMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReviewCreatedDomainEventHandlerTest {

    @Mock
    private ReviewRepository repository;

    private ReviewCreatedDomainEventHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new ReviewCreatedDomainEventHandler(repository);
    }

    @Test
    public void given_that_a_review_have_been_created_when_handle_the_event_then_review_is_created() {
        handler.handle(new ReviewCreatedDomainEvent(
                ReviewIdMother.random(),
                CourseIdMother.random(),
                StudentIdMother.random(),
                ReviewRatingMother.random(),
                ReviewTextMother.random(),
                CreatedOnMother.random()
        ));

        verify(repository).save(any());
    }
}
