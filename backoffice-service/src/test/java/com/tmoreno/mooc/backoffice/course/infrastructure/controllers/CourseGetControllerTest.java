package com.tmoreno.mooc.backoffice.course.infrastructure.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClass;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.common.E2ETest;
import com.tmoreno.mooc.backoffice.common.DatabaseUtils;
import com.tmoreno.mooc.shared.domain.Identifier;
import com.tmoreno.mooc.shared.domain.TeacherId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class CourseGetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        DatabaseUtils databaseUtils = new DatabaseUtils(jdbcTemplate);
        databaseUtils.initialize();
    }

    @Test
    void should_return_404_status_code_when_course_does_not_exists() throws Exception {
        mockMvc
            .perform(get("/courses/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-not-found"));
    }

    @Test
    void should_return_200_status_code_and_empty_response_when_there_are_not_courses() throws Exception {
        mockMvc
            .perform(get("/courses").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void should_return_200_status_code_and_course_data() throws Exception {
        Course course = CourseMother.random();
        courseRepository.save(course);

        String responseString = mockMvc
            .perform(get("/courses/" + course.getId().getValue()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        JsonNode responseJson = toJson(responseString);

        assertCourse(responseJson, course);
    }

    private JsonNode toJson(String content) throws JsonProcessingException {
        return objectMapper.readTree(content);
    }

    private void assertCourse(JsonNode jsonCourse, Course course) {
        assertThat(jsonCourse.get("id").asText(), is(course.getId().getValue()));
        assertThat(jsonCourse.get("title").asText(), is(course.getTitle().value()));
        assertThat(jsonCourse.get("imageUrl").asText(), is(course.getImageUrl().get().value()));
        assertThat(jsonCourse.get("summary").asText(), is(course.getSummary().get().value()));
        assertThat(jsonCourse.get("description").asText(), is(course.getDescription().get().value()));
        assertThat(jsonCourse.get("state").asText(), is(course.getState().name()));
        assertThat(jsonCourse.get("language").asText(), is(course.getLanguage().get().name()));
        assertThat(jsonCourse.get("priceValue").asDouble(), is(course.getPrice().get().value()));
        assertThat(jsonCourse.get("priceCurrency").asText(), is(course.getPrice().get().currency().getCurrencyCode()));
        assertSections(jsonCourse.get("sections"), course.getSections());
        assertReviews(jsonCourse.get("reviews"), course.getReviews());
        assertStudents(jsonCourse.get("students"), course.getStudents());
        assertTeachers(jsonCourse.get("teachers"), course.getTeachers());
    }

    private void assertSections(JsonNode jsonSections, List<Section> sections) {
        assertThat(jsonSections.size(), is(sections.size()));

        for (JsonNode jsonSection : jsonSections) {
            Section section = sections
                .stream()
                .filter(s -> jsonSection.get("id").asText().equals(s.getId().getValue()))
                .findFirst()
                .orElseThrow();

            assertThat(jsonSection.get("title").asText(), is(section.getTitle().value()));
            assertSectionClasses(jsonSection.get("classes"), section.getClasses());
        }
    }

    private void assertSectionClasses(JsonNode jsonClasses, List<SectionClass> classes) {
        assertThat(jsonClasses.size(), is(classes.size()));

        for (JsonNode jsonClass : jsonClasses) {
            SectionClass sectionClass = classes
                .stream()
                .filter(s -> jsonClass.get("id").asText().equals(s.getId().getValue()))
                .findFirst()
                .orElseThrow();

            assertThat(jsonClass.get("title").asText(), is(sectionClass.getTitle().value()));
            assertThat(jsonClass.get("duration").asInt(), is(sectionClass.getDuration().value()));
        }
    }

    private void assertReviews(JsonNode jsonReviews, Map<StudentId, ReviewId> reviews) {
        assertThat(jsonReviews.size(), is(reviews.size()));

        for (JsonNode jsonReview : jsonReviews) {
            ReviewId reviewId = reviews.get(new StudentId(jsonReview.get("studentId").asText()));

            assertThat(jsonReview.get("reviewId").asText(), is(reviewId.getValue()));
        }
    }

    private void assertStudents(JsonNode jsonStudents, Set<StudentId> students) {
        assertThat(jsonStudents.size(), is(students.size()));

        Set<String> studentsString = students.stream().map(Identifier::getValue).collect(Collectors.toSet());

        for (JsonNode jsonStudent : jsonStudents) {
            assertThat(studentsString, hasItem(jsonStudent.asText()));
        }
    }

    private void assertTeachers(JsonNode jsonTeachers, Set<TeacherId> teachers) {
        assertThat(jsonTeachers.size(), is(teachers.size()));

        Set<String> teachersString = teachers.stream().map(Identifier::getValue).collect(Collectors.toSet());

        for (JsonNode jsonTeacher : jsonTeachers) {
            assertThat(teachersString, hasItem(jsonTeacher.asText()));
        }
    }
}
