package com.tmoreno.mooc.backoffice.teacher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import com.tmoreno.mooc.shared.domain.Identifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.stream.Collectors;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class TeacherGetControllerIT extends BaseControllerIT {

    @SpyBean
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {
        url += "/teachers";
    }

    @Test
    public void given_existing_teacher_when_send_get_request_then_receive_teacher_data() throws JsonProcessingException {
        CourseId courseId = CourseIdMother.random();
        Teacher teacher = TeacherMother.randomWithCourse(courseId);

        teacherRepository.save(teacher);

        ResponseEntity<String> response = get(teacher.getId().getValue());

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());
        assertTeacher(responseBody, teacher);
    }

    @Test
    public void given_not_existing_teacher_when_send_get_request_then_receive_not_found_response() {
        ResponseEntity<String> response = get(TeacherIdMother.random().getValue());

        assertNotFound(response);
    }

    @Test
    public void given_there_are_no_teachers_when_send_get_request_then_receive_empty_response() throws JsonProcessingException {
        ResponseEntity<String> response = get();

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());

        assertThat(responseBody.size(), is(0));
    }

    @Test
    public void given_there_are_three_teachers_when_send_get_request_then_receive_three_teachers_data() throws JsonProcessingException {
        Teacher teacher1 = TeacherMother.random();
        teacherRepository.save(teacher1);

        CourseId courseId = CourseIdMother.random();
        Teacher teacher2 = TeacherMother.randomWithCourse(courseId);
        teacherRepository.save(teacher2);

        Teacher teacher3 = TeacherMother.random();
        teacherRepository.save(teacher3);

        ResponseEntity<String> response = get();

        assertOk(response);

        JsonNode responseBody = toJson(response.getBody());

        assertThat(responseBody.size(), is(3));

        assertTeacher(responseBody.get(0), teacher1);
        assertTeacher(responseBody.get(1), teacher2);
        assertTeacher(responseBody.get(2), teacher3);
    }

    private void assertTeacher(JsonNode jsonTeacher, Teacher teacher) {
        Set<String> teacherCourses = teacher.getCourses()
                .stream()
                .map(Identifier::getValue)
                .collect(Collectors.toSet());

        assertThat(jsonTeacher.get("id").asText(), is(teacher.getId().getValue()));
        assertThat(jsonTeacher.get("name").asText(), is(teacher.getName().getValue()));
        assertThat(jsonTeacher.get("email").asText(), is(teacher.getEmail().getValue()));
        assertThat(jsonTeacher.get("courses").size(), is(teacher.getCourses().size()));

        for (JsonNode course : jsonTeacher.get("courses")) {
            assertThat(teacherCourses, hasItem(course.asText()));
        }
    }
}
