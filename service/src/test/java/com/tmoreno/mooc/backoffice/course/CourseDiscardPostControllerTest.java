package com.tmoreno.mooc.backoffice.course;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.support.E2ETest;
import com.tmoreno.mooc.backoffice.utils.DatabaseUtils;
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
public class CourseDiscardPostControllerTest {

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
    void should_return_200_status_code_when_discard_a_published_course() throws Exception {
        Course course = CourseMother.randomInPublishState();
        courseRepository.save(course);

        mockMvc
            .perform(post("/courses/" + course.getId().getValue() + "/discard").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void should_return_412_status_code_when_discard_a_discarded_course() throws Exception {
        Course course = CourseMother.randomInDiscardedState();
        courseRepository.save(course);

        mockMvc
            .perform(post("/courses/" + course.getId().getValue() + "/discard").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isPreconditionFailed())
            .andExpect(jsonPath("$.code").value("discard-course"));
    }

    @Test
    void should_return_404_status_code_when_discard_a_not_existing_course() throws Exception {
        mockMvc
            .perform(post("/courses/" + UUID.randomUUID() + "/discard").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-not-found"));
    }
}
