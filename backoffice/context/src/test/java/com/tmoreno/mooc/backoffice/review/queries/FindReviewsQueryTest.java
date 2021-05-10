package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.backoffice.mothers.ReviewMother;
import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.shared.query.VoidQueryParams;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindReviewsQueryTest {

    @Mock
    private ReviewRepository repository;

    private FindReviewsQuery query;

    @BeforeEach
    public void setUp() {
        query = new FindReviewsQuery(repository);
    }

    @Test
    public void given_no_reviews_when_find_then_return_empty_response() {
        FindReviewsQueryResponse response = query.execute(new VoidQueryParams());

        assertThat(response.getReviews(), Matchers.is(empty()));
    }

    @Test
    public void given_there_are_three_reviews_when_find_then_return_three_reviews() {
        Review review1 = ReviewMother.random();
        Review review2 = ReviewMother.random();
        Review review3 = ReviewMother.random();

        when(repository.findAll()).thenReturn(List.of(review1, review2, review3));

        FindReviewsQueryResponse response = query.execute(new VoidQueryParams());

        assertThat(response.getReviews().size(), is(3));
        assertReview(response.getReviews().get(0), review1);
        assertReview(response.getReviews().get(1), review2);
        assertReview(response.getReviews().get(2), review3);
    }

    private void assertReview(FindReviewQueryResponse response, Review review) {
        assertThat(response.getId(), is(review.getId().getValue()));
        assertThat(response.getCourseId(), is(review.getCourseId().getValue()));
        assertThat(response.getStudentId(), is(review.getStudentId().getValue()));
        assertThat(response.getRating(), is(review.getRating().getValue()));
        assertThat(response.getText(), is(review.getText().getValue()));
        assertThat(response.getCreatedOn(), is(review.getCreatedOn().getValue()));
    }
}
