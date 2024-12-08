package com.tmoreno.mooc.backoffice.course;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionTitleMother;
import com.tmoreno.mooc.backoffice.support.E2ETest;
import com.tmoreno.mooc.backoffice.utils.DatabaseUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class CourseSectionPostControllerTest {

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
    void should_return_201_status_code_when_create_a_section() throws Exception {
        Course course = CourseMother.randomInDraftState();
        courseRepository.save(course);

        String content = """
        {
            "sectionId": "%s",
            "title": "%s"
        }
        """.formatted(
            SectionIdMother.random().getValue(),
            SectionTitleMother.random().getValue()
        );

        mockMvc
            .perform(
                post(String.format("/courses/%s/sections", course.getId().getValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
            )
            .andExpect(status().isCreated());
    }

    @Test
    void should_return_404_status_code_when_course_does_not_exists() throws Exception {
        String content = """
        {
            "sectionId": "%s",
            "title": "%s"
        }
        """.formatted(
            SectionIdMother.random().getValue(),
            SectionTitleMother.random().getValue()
        );

        mockMvc
            .perform(
                post(String.format("/courses/%s/sections", CourseIdMother.random().getValue()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-not-found"));
    }
}
