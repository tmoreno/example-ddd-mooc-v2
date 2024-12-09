package com.tmoreno.mooc.backoffice.course.infrastructure.controllers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.E2ETest;
import com.tmoreno.mooc.backoffice.common.DatabaseUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class CoursePostControllerTest {

    private static final String ANY_COURSE_ID = UUID.randomUUID().toString();
    private static final String ANY_COURSE_TITLE = RandomStringUtils.randomAlphabetic(10, 100);

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
    void should_return_412_status_code_when_create_a_course_with_the_same_id_than_existing_one() throws Exception {
        Course course = CourseMother.random();
        courseRepository.save(course);

        String content = """
            {
                "id": "%s",
                "title": "%s"
            }
            """.formatted(course.getId().getValue(), ANY_COURSE_TITLE);

        mockMvc
            .perform(post("/courses").contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isPreconditionFailed())
            .andExpect(jsonPath("$.code").value("course-exists"));
    }

    @Test
    void should_return_412_status_code_when_create_a_course_with_the_same_title_than_existing_one() throws Exception {
        Course course = CourseMother.random();
        courseRepository.save(course);

        String content = """
            {
                "id": "%s",
                "title": "%s"
            }
            """.formatted(ANY_COURSE_ID, course.getTitle().getValue());

        mockMvc
            .perform(post("/courses").contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isPreconditionFailed())
            .andExpect(jsonPath("$.code").value("course-exists"));
    }

    @Test
    void should_return_201_status_code_when_create_a_course() throws Exception {
        String content = """
            {
                "id": "%s",
                "title": "%s"
            }
            """.formatted(ANY_COURSE_ID, ANY_COURSE_TITLE);

        mockMvc
            .perform(post("/courses").contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").doesNotExist());
    }
}
