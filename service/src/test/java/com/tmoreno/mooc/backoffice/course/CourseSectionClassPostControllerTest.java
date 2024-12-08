package com.tmoreno.mooc.backoffice.course;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.SectionClassIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionClassTitleMother;
import com.tmoreno.mooc.backoffice.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionTitleMother;
import com.tmoreno.mooc.backoffice.support.E2ETest;
import com.tmoreno.mooc.backoffice.utils.DatabaseUtils;
import com.tmoreno.mooc.shared.mothers.DurationInSecondsMother;
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
public class CourseSectionClassPostControllerTest {

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
    void should_return_201_status_code_when_create_a_class_in_a_section() throws Exception {
        SectionId sectionId = SectionIdMother.random();
        Course course = CourseMother.randomInDraftState();
        course.addSection(sectionId, SectionTitleMother.random());
        courseRepository.save(course);

        String content = """
        {
            "sectionClassId": "%s",
            "title": "%s",
            "duration": %d
        }
        """
        .formatted(
            SectionClassIdMother.random().getValue(),
            SectionClassTitleMother.random().getValue(),
            DurationInSecondsMother.random().getValue()
        );

        mockMvc
            .perform(
                post(
                    String.format(
                        "/courses/%s/sections/%s/classes",
                        course.getId().getValue(),
                        sectionId.getValue()
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
            )
            .andExpect(status().isCreated());
    }

    @Test
    void should_return_404_status_code_when_create_a_class_for_a_not_existing_course() throws Exception {
        String content = """
        {
            "sectionClassId": "%s",
            "title": "%s",
            "duration": %d
        }
        """
        .formatted(
            SectionClassIdMother.random().getValue(),
            SectionClassTitleMother.random().getValue(),
            DurationInSecondsMother.random().getValue()
        );

        mockMvc
            .perform(
                post(
                    String.format(
                        "/courses/%s/sections/%s/classes",
                        CourseIdMother.random().getValue(),
                        SectionIdMother.random().getValue()
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-not-found"));
    }

    @Test
    void should_return_404_status_code_when_create_a_class_for_a_not_existing_section() throws Exception {
        Course course = CourseMother.randomInDraftState();
        course.addSection(SectionIdMother.random(), SectionTitleMother.random());
        courseRepository.save(course);

        String content = """
        {
            "sectionClassId": "%s",
            "title": "%s",
            "duration": %d
        }
        """
        .formatted(
            SectionClassIdMother.random().getValue(),
            SectionClassTitleMother.random().getValue(),
            DurationInSecondsMother.random().getValue()
        );

        mockMvc
            .perform(
                post(
                    String.format(
                        "/courses/%s/sections/%s/classes",
                        course.getId().getValue(),
                        SectionIdMother.random().getValue()
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-section-not-found"));
    }
}
