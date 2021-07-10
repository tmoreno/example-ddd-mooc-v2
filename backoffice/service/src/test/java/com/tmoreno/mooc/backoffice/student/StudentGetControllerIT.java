package com.tmoreno.mooc.backoffice.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.mothers.StudentIdMother;
import com.tmoreno.mooc.backoffice.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
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

public class StudentGetControllerIT extends BaseControllerIT {

    @SpyBean
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        url += "/students";
    }

    @Test
    public void given_existing_student_when_send_get_request_then_receive_student_data() throws JsonProcessingException {
        CourseId courseId = CourseIdMother.random();
        ReviewId reviewId = ReviewIdMother.random();
        Student student = StudentMother.randomWithCourseAndReview(courseId, reviewId);

        studentRepository.save(student);

        ResponseEntity<String> response = get(student.getId().getValue());

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());
        assertStudent(responseBody, student);
    }

    @Test
    public void given_not_existing_student_when_send_get_request_then_receive_not_found_response() throws JsonProcessingException {
        ResponseEntity<String> response = get(StudentIdMother.random().getValue());

        assertNotFound(response);

        assertErrorCode(toJson(response.getBody()), "student-not-found");
    }

    @Test
    public void given_there_are_no_students_when_send_get_request_then_receive_empty_response() throws JsonProcessingException {
        ResponseEntity<String> response = get();

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());

        assertThat(responseBody.size(), is(0));
    }

    @Test
    public void given_there_are_three_students_when_send_get_request_then_receive_three_students_data() throws JsonProcessingException {
        Student student1 = StudentMother.random();
        studentRepository.save(student1);

        Student student2 = StudentMother.randomWithCourse(CourseIdMother.random());
        studentRepository.save(student2);

        Student student3 = StudentMother.randomWithCourseAndReview(CourseIdMother.random(), ReviewIdMother.random());
        studentRepository.save(student3);

        ResponseEntity<String> response = get();

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());

        assertThat(responseBody.size(), is(3));

        for (JsonNode jsonStudent : responseBody) {
            Student student = List.of(student1, student2, student3)
                    .stream()
                    .filter(t -> t.getId().getValue().equals(jsonStudent.get("id").asText()))
                    .findFirst()
                    .orElseThrow();

            assertStudent(jsonStudent, student);
        }
    }

    private void assertStudent(JsonNode jsonStudent, Student student) {
        Set<String> studentCourses = student.getCourses()
                .stream()
                .map(Identifier::getValue)
                .collect(Collectors.toSet());

        Map<String, String> studentReviews = student.getReviews()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getValue(), entry -> entry.getValue().getValue()));

        assertThat(jsonStudent.get("id").asText(), is(student.getId().getValue()));
        assertThat(jsonStudent.get("name").asText(), is(student.getName().getValue()));
        assertThat(jsonStudent.get("email").asText(), is(student.getEmail().getValue()));

        assertThat(jsonStudent.get("courses").size(), is(student.getCourses().size()));
        for (JsonNode course : jsonStudent.get("courses")) {
            assertThat(studentCourses, hasItem(course.asText()));
        }

        assertThat(jsonStudent.get("reviews").size(), is(student.getReviews().size()));
        for (JsonNode jsonReview : jsonStudent.get("reviews")) {
            String reviewId = studentReviews.get(jsonReview.get("courseId").asText());

            assertThat(jsonReview.get("reviewId").asText(), is(reviewId));
        }
    }
}
