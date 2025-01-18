package com.tmoreno.mooc.backoffice.course.infrastructure.controllers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.common.mothers.CourseDescriptionMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseImageUrlMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseSummaryMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseTitleMother;
import com.tmoreno.mooc.backoffice.common.E2ETest;
import com.tmoreno.mooc.backoffice.common.DatabaseUtils;
import com.tmoreno.mooc.shared.domain.Price;
import com.tmoreno.mooc.shared.mothers.LanguageMother;
import com.tmoreno.mooc.shared.mothers.PriceMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class CoursePutControllerTest {

    private static final String ANY_COURSE_TITLE = CourseTitleMother.random().getValue();
    private static final String ANY_IMAGE_URL = CourseImageUrlMother.random().getValue();
    private static final String ANY_SUMMARY = CourseSummaryMother.random().getValue();
    private static final String ANY_DESCRIPTION = CourseDescriptionMother.random().getValue();
    private static final String ANY_LANGUAGE = LanguageMother.random().name();
    private static final Price ANY_PRICE = PriceMother.random();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        DatabaseUtils databaseUtils = new DatabaseUtils(jdbcTemplate);
        databaseUtils.initialize();
    }

    @Test
    void should_return_404_status_code_when_update_a_not_existing_course() throws Exception {
        mockMvc
            .perform(put("/courses/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void should_return_200_status_code_when_update_a_course_in_draft_state() throws Exception {
        Course course = CourseMother.randomInDraftState();
        courseRepository.save(course);

        String content = """
            {
                "title": "%s",
                "imageUrl": "%s",
                "summary": "%s",
                "description": "%s",
                "language": "%s",
                "priceValue": %s,
                "priceCurrency": "%s"
            }
            """.formatted(
                ANY_COURSE_TITLE,
                ANY_IMAGE_URL,
                ANY_SUMMARY,
                ANY_DESCRIPTION,
                ANY_LANGUAGE,
                ANY_PRICE.value(),
                ANY_PRICE.currency().getCurrencyCode()
            );

        mockMvc
            .perform(put("/courses/" + course.getId().getValue()).contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isOk());
    }
}
