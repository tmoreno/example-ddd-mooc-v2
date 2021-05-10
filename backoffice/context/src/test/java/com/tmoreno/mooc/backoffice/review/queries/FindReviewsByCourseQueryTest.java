package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.ReviewMother;
import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
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
public class FindReviewsByCourseQueryTest {

    @Mock
    private ReviewRepository repository;

    private FindReviewsByCourseQuery query;

    @BeforeEach
    public void setUp() {
        query = new FindReviewsByCourseQuery(repository);
    }

    @Test
    public void given_no_reviews_when_find_then_return_empty_response() {
        FindReviewsByCourseQueryParams params = new FindReviewsByCourseQueryParams();
        params.setCourseId(CourseIdMother.random().getValue());

        FindReviewsQueryResponse response = query.execute(params);

        assertThat(response.getReviews(), is(empty()));
    }

    @Test
    public void given_there_a_review_for_a_course_when_find_then_return_the_course_review() {
        Review review = ReviewMother.random();

        when(repository.findByCourse(review.getCourseId())).thenReturn(List.of(review));

        FindReviewsByCourseQueryParams params = new FindReviewsByCourseQueryParams();
        params.setCourseId(review.getCourseId().getValue());

        FindReviewsQueryResponse response = query.execute(params);

        assertThat(response.getReviews().size(), is(1));
        assertThat(response.getReviews().get(0).getId(), is(review.getId().getValue()));
        assertThat(response.getReviews().get(0).getCourseId(), is(review.getCourseId().getValue()));
        assertThat(response.getReviews().get(0).getStudentId(), is(review.getStudentId().getValue()));
        assertThat(response.getReviews().get(0).getRating(), is(review.getRating().getValue()));
        assertThat(response.getReviews().get(0).getText(), is(review.getText().getValue()));
        assertThat(response.getReviews().get(0).getCreatedOn(), is(review.getCreatedOn().getValue()));
    }
}
