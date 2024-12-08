package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.backoffice.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.mothers.ReviewMother;
import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.backoffice.review.domain.exceptions.ReviewNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindReviewQueryTest {

    @Mock
    private ReviewRepository repository;

    private FindReviewQuery query;

    @BeforeEach
    public void setUp() {
        query = new FindReviewQuery(repository);
    }

    @Test
    public void given_a_existing_review_id_when_find_then_return_the_review() {
        ReviewId reviewId = ReviewIdMother.random();
        Review review = ReviewMother.random();

        when(repository.find(reviewId)).thenReturn(Optional.of(review));

        FindReviewQueryParams params = new FindReviewQueryParams();
        params.setReviewId(reviewId.getValue());

        FindReviewQueryResponse response = query.execute(params);

        assertThat(response.getId(), is(review.getId().getValue()));
        assertThat(response.getCourseId(), is(review.getCourseId().getValue()));
        assertThat(response.getStudentId(), is(review.getStudentId().getValue()));
        assertThat(response.getRating(), is(review.getRating().getValue()));
        assertThat(response.getText(), is(review.getText().getValue()));
        assertThat(response.getCreatedOn(), is(review.getCreatedOn().getValue()));
    }

    @Test
    public void given_a_not_existing_review_id_when_find_then_throws_an_exception() {
        Assertions.assertThrows(ReviewNotFoundException.class, () -> {
            ReviewId reviewId = ReviewIdMother.random();

            when(repository.find(reviewId)).thenReturn(Optional.empty());

            FindReviewQueryParams params = new FindReviewQueryParams();
            params.setReviewId(reviewId.getValue());

            query.execute(params);
        });
    }
}
