package com.tmoreno.mooc.backoffice.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClass;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import com.tmoreno.mooc.shared.domain.Identifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertErrorCode;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class CourseGetControllerIT extends BaseControllerIT {

    @SpyBean
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        url += "/courses";
    }

    @Test
    public void given_an_existing_course_when_send_get_request_then_receive_course_data() throws JsonProcessingException {
        Course course = CourseMother.random();

        courseRepository.save(course);

        ResponseEntity<String> response = get(course.getId().getValue());

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());
        assertCourse(responseBody, course);
    }

    @Test
    public void given_not_existing_course_when_send_get_request_then_receive_not_found_response() throws JsonProcessingException {
        ResponseEntity<String> response = get(CourseIdMother.random().getValue());

        assertNotFound(response);

        assertErrorCode(toJson(response.getBody()), "course-not-found");
    }

    @Test
    public void given_there_are_no_courses_when_send_get_request_then_receive_empty_response() throws JsonProcessingException {
        ResponseEntity<String> response = get();

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());

        assertThat(responseBody.size(), is(0));
    }

    @Test
    public void given_there_are_three_courses_when_send_get_request_then_receive_three_courses_data() throws JsonProcessingException {
        Course course1 = CourseMother.random();
        courseRepository.save(course1);

        Course course2 = CourseMother.random();
        courseRepository.save(course2);

        Course course3 = CourseMother.random();
        courseRepository.save(course3);

        ResponseEntity<String> response = get();

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());

        assertThat(responseBody.size(), is(3));

        for (JsonNode jsonCourse : responseBody) {
            Course course = List.of(course1, course2, course3)
                    .stream()
                    .filter(t -> t.getId().getValue().equals(jsonCourse.get("id").asText()))
                    .findFirst()
                    .orElseThrow();

            assertCourse(jsonCourse, course);
        }
    }

    private void assertCourse(JsonNode jsonCourse, Course course) {
        assertThat(jsonCourse.get("id").asText(), is(course.getId().getValue()));
        assertThat(jsonCourse.get("title").asText(), is(course.getTitle().getValue()));
        assertThat(jsonCourse.get("imageUrl").asText(), is(course.getImageUrl().get().getValue()));
        assertThat(jsonCourse.get("summary").asText(), is(course.getSummary().get().getValue()));
        assertThat(jsonCourse.get("description").asText(), is(course.getDescription().get().getValue()));
        assertThat(jsonCourse.get("state").asText(), is(course.getState().name()));
        assertThat(jsonCourse.get("language").asText(), is(course.getLanguage().get().name()));
        assertThat(jsonCourse.get("priceValue").asDouble(), is(course.getPrice().get().getValue()));
        assertThat(jsonCourse.get("priceCurrency").asText(), is(course.getPrice().get().getCurrency().getCurrencyCode()));
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

            assertThat(jsonSection.get("title").asText(), is(section.getTitle().getValue()));
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

            assertThat(jsonClass.get("title").asText(), is(sectionClass.getTitle().getValue()));
            assertThat(jsonClass.get("duration").asInt(), is(sectionClass.getDuration().getValue()));
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
