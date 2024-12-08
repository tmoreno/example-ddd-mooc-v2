package com.tmoreno.mooc.backoffice.course;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.support.E2ETest;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
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
public class CourseTeacherPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {
        DatabaseUtils databaseUtils = new DatabaseUtils(jdbcTemplate);
        databaseUtils.initialize();
    }

    @Test
    void should_return_404_when_add_a_teacher_to_a_not_existing_course() throws Exception {
        Teacher teacher = TeacherMother.random();
        teacherRepository.save(teacher);

        String content = """
        {
            "teacherId": "%s"
        }
        """.formatted(
            teacher.getId().getValue()
        );

        mockMvc
            .perform(
                post(String.format("/courses/%s/teachers", CourseIdMother.random().getValue()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-not-found"));
    }

    @Test
    void should_return_404_when_add_a_not_existing_teacher_to_a_course() throws Exception {
        Course course = CourseMother.randomInDraftState();
        courseRepository.save(course);

        String content = """
        {
            "teacherId": "%s"
        }
        """.formatted(
            TeacherIdMother.random().getValue()
        );

        mockMvc
            .perform(
                post(String.format("/courses/%s/teachers", course.getId().getValue()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("teacher-not-found"));
    }

    @Test
    void should_return_200_when_add_teacher_to_course() throws Exception {
        Course course = CourseMother.randomInDraftState();
        courseRepository.save(course);

        Teacher teacher = TeacherMother.random();
        teacherRepository.save(teacher);

        String content = """
        {
            "teacherId": "%s"
        }
        """.formatted(
            teacher.getId().getValue()
        );

        mockMvc
            .perform(
                post(String.format("/courses/%s/teachers", course.getId().getValue()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andExpect(status().isCreated());
    }
}
