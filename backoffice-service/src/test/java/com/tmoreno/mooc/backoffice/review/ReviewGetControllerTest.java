package com.tmoreno.mooc.backoffice.review;

import com.tmoreno.mooc.backoffice.support.E2ETest;
import com.tmoreno.mooc.backoffice.utils.DatabaseUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class ReviewGetControllerTest {

    private static final String ANY_REVIEW_ID = UUID.randomUUID().toString();
    private static final String ANY_COURSE_ID = UUID.randomUUID().toString();
    private static final String ANY_STUDENT_ID = UUID.randomUUID().toString();
    private static final String ANY_RATING_TEXT = "ONE_AND_A_HALF";
    private static final double ANY_RATING_VALUE = 1.5;
    private static final String ANY_TEXT = RandomStringUtils.randomAlphabetic(100, 5000);
    private static final Instant ANY_CREATION_INSTANT = Instant.now().truncatedTo(ChronoUnit.SECONDS);
    private static final String OTHER_REVIEW_ID = UUID.randomUUID().toString();
    private static final String OTHER_COURSE_ID = UUID.randomUUID().toString();
    private static final String OTHER_STUDENT_ID = UUID.randomUUID().toString();
    private static final String OTHER_RATING_TEXT = "ONE_AND_A_HALF";
    private static final String OTHER_TEXT = RandomStringUtils.randomAlphabetic(100, 5000);
    private static final Instant OTHER_CREATION_INSTANT = Instant.now().truncatedTo(ChronoUnit.SECONDS);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DatabaseUtils databaseUtils = new DatabaseUtils(jdbcTemplate);
        databaseUtils.initialize();
    }

    @Test
    void should_return_404_and_review_not_found_code_when_review_not_found() throws Exception {
        mockMvc
            .perform(get("/reviews/" + ANY_REVIEW_ID))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("review-not-found"))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Review not found: " + ANY_REVIEW_ID))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_200_and_empty_body_when_there_are_not_reviews() throws Exception {
        mockMvc
            .perform(get("/reviews"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void should_return_200_and_review_data_when_review_exists() throws Exception {
        createReview(ANY_REVIEW_ID, ANY_COURSE_ID, ANY_STUDENT_ID, ANY_RATING_TEXT, ANY_TEXT, ANY_CREATION_INSTANT);

        mockMvc
            .perform(get("/reviews/" + ANY_REVIEW_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ANY_REVIEW_ID))
            .andExpect(jsonPath("$.courseId").value(ANY_COURSE_ID))
            .andExpect(jsonPath("$.studentId").value(ANY_STUDENT_ID))
            .andExpect(jsonPath("$.rating").value(ANY_RATING_VALUE))
            .andExpect(jsonPath("$.text").value(ANY_TEXT))
            .andExpect(jsonPath("$.createdOn").value(ANY_CREATION_INSTANT.toEpochMilli()));
    }

    @Test
    void should_return_200_and_all_reviews() throws Exception {
        createReview(ANY_REVIEW_ID, ANY_COURSE_ID, ANY_STUDENT_ID, ANY_RATING_TEXT, ANY_TEXT, ANY_CREATION_INSTANT);
        createReview(OTHER_REVIEW_ID, OTHER_COURSE_ID, OTHER_STUDENT_ID, OTHER_RATING_TEXT, OTHER_TEXT, OTHER_CREATION_INSTANT);

        mockMvc
            .perform(get("/reviews"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[?(@.id == '" + ANY_REVIEW_ID + "')].courseId").value(ANY_COURSE_ID))
            .andExpect(jsonPath("$[?(@.id == '" + OTHER_REVIEW_ID + "')].courseId").value(OTHER_COURSE_ID));
    }

    private void createReview(String reviewId, String courseId, String studentId, String rating, String text, Instant creationInstant) {
        jdbcTemplate.update(
            "insert into reviews (id, student_id, course_id, rating, text, created_on) values (?, ?, ?, ?, ?, ?)",
            reviewId,
            studentId,
            courseId,
            rating,
            text,
            creationInstant
        );
    }
}
