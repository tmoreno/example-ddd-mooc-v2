package com.tmoreno.mooc.backoffice.course.infrastructure.controllers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionTitleMother;
import com.tmoreno.mooc.backoffice.common.E2ETest;
import com.tmoreno.mooc.backoffice.common.DatabaseUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class CourseSectionPutControllerTest {

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
    void should_return_404_status_code_when_update_a_section_for_a_not_existing_course() throws Exception {
        String content = """
        {
            "title": "%s"
        }
        """.formatted(
            SectionTitleMother.random().value()
        );

        mockMvc
            .perform(
                put(String.format(
                    "/courses/%s/sections/%s",
                    CourseIdMother.random().getValue(),
                    SectionIdMother.random().getValue()
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-not-found"));
    }

    @Test
    void should_return_404_when_section_does_not_exist() throws Exception {
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);
        courseRepository.save(course);

        String content = """
        {
            "title": "%s"
        }
        """.formatted(
            SectionTitleMother.random().value()
        );

        mockMvc
            .perform(
                put(String.format(
                    "/courses/%s/sections/%s",
                    course.getId().getValue(),
                    SectionIdMother.random().getValue()
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-section-not-found"));
    }

    @Test
    void should_return_200_status_code_when_update_a_section() throws Exception {
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        courseRepository.save(course);

        String content = """
        {
            "title": "%s"
        }
        """.formatted(
            SectionTitleMother.random().value()
        );

        mockMvc
            .perform(
                put(String.format(
                    "/courses/%s/sections/%s",
                    course.getId().getValue(),
                    section.getId().getValue()
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
            )
            .andExpect(status().isOk());
    }
}
