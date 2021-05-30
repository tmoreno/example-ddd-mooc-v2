package com.tmoreno.mooc.backoffice.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.tmoreno.mooc.backoffice.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.mothers.ReviewMother;
import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReviewGetControllerIT extends BaseControllerIT {

    @SpyBean
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp() {
        url += "/reviews";
    }

    @Test
    public void given_an_existing_review_when_send_get_request_then_receive_review_data() throws JsonProcessingException {
        Review review = ReviewMother.random();

        reviewRepository.save(review);

        ResponseEntity<String> response = get(review.getId().getValue());

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());
        assertReview(responseBody, review);
    }

    @Test
    public void given_not_existing_review_when_send_get_request_then_receive_not_found_response() {
        ResponseEntity<String> response = get(ReviewIdMother.random().getValue());

        assertNotFound(response);
    }

    @Test
    public void given_there_are_no_reviews_when_send_get_request_then_receive_empty_response() throws JsonProcessingException {
        ResponseEntity<String> response = get();

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());

        assertThat(responseBody.size(), is(0));
    }

    @Test
    public void given_there_are_three_reviews_when_send_get_request_then_receive_three_reviews_data() throws JsonProcessingException {
        Review review1 = ReviewMother.random();
        reviewRepository.save(review1);

        Review review2 = ReviewMother.random();
        reviewRepository.save(review2);

        Review review3 = ReviewMother.random();
        reviewRepository.save(review3);

        ResponseEntity<String> response = get();

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());

        assertThat(responseBody.size(), is(3));

        for (JsonNode jsonReview : responseBody) {
            Review review = List.of(review1, review2, review3)
                    .stream()
                    .filter(t -> t.getId().getValue().equals(jsonReview.get("id").asText()))
                    .findFirst()
                    .orElseThrow();

            assertReview(jsonReview, review);
        }
    }

    private void assertReview(JsonNode jsonReview, Review review) {
        assertThat(jsonReview.get("id").asText(), is(review.getId().getValue()));
        assertThat(jsonReview.get("courseId").asText(), is(review.getCourseId().getValue()));
        assertThat(jsonReview.get("studentId").asText(), is(review.getStudentId().getValue()));
        assertThat(jsonReview.get("rating").asDouble(), is(review.getRating().getValue()));
        assertThat(jsonReview.get("text").asText(), is(review.getText().getValue()));
        assertThat(jsonReview.get("createdOn").asLong(), is(review.getCreatedOn().getValue()));
    }
}
