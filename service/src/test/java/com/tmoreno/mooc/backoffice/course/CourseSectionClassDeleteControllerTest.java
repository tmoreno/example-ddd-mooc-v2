package com.tmoreno.mooc.backoffice.course;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.SectionClassIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionClassTitleMother;
import com.tmoreno.mooc.backoffice.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.mothers.SectionMother;
import com.tmoreno.mooc.backoffice.support.E2ETest;
import com.tmoreno.mooc.backoffice.utils.DatabaseUtils;
import com.tmoreno.mooc.shared.mothers.DurationInSecondsMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class CourseSectionClassDeleteControllerTest {

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
    void should_return_404_status_code_when_delete_a_section_class_of_a_not_existing_course() throws Exception {
        mockMvc
            .perform(
                delete(
                    String.format(
                        "/courses/%s/sections/%s/classes/%s",
                        CourseIdMother.random().getValue(),
                        SectionIdMother.random().getValue(),
                        SectionClassIdMother.random().getValue()
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-not-found"));
    }

    @Test
    void should_return_404_status_code_when_delete_a_section_class_of_a_not_existing_section() throws Exception {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        Section section = SectionMother.randomWithClass(sectionClassId, SectionClassTitleMother.random(), DurationInSecondsMother.random());
        Course course = CourseMother.randomInDraftStateWithSection(section);
        courseRepository.save(course);

        mockMvc
            .perform(
                delete(
                    String.format(
                        "/courses/%s/sections/%s/classes/%s",
                        course.getId().getValue(),
                        SectionIdMother.random().getValue(),
                        SectionClassIdMother.random().getValue()
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-section-not-found"));
    }

    @Test
    void should_return_404_status_code_when_delete_a_not_existing_section_class() throws Exception {
        Section section = SectionMother.randomWithClass(SectionClassIdMother.random(), SectionClassTitleMother.random(), DurationInSecondsMother.random());
        Course course = CourseMother.randomInDraftStateWithSection(section);
        courseRepository.save(course);

        mockMvc
            .perform(
                delete(
                    String.format(
                        "/courses/%s/sections/%s/classes/%s",
                        course.getId().getValue(),
                        section.getId().getValue(),
                        SectionClassIdMother.random().getValue()
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-section-class-not-found"));
    }

    @Test
    void should_return_200_status_code_when_delete_a_section_class_of_a_course_in_draft_state() throws Exception {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        Section section = SectionMother.randomWithClass(sectionClassId, SectionClassTitleMother.random(), DurationInSecondsMother.random());
        Course course = CourseMother.randomInDraftStateWithSection(section);
        courseRepository.save(course);

        mockMvc
            .perform(
                delete(
                    String.format(
                        "/courses/%s/sections/%s/classes/%s",
                        course.getId().getValue(),
                        section.getId().getValue(),
                        sectionClassId.getValue()
                    )
                )
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
}
