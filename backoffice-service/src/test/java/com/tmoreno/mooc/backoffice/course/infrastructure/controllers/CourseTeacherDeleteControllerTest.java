package com.tmoreno.mooc.backoffice.course.infrastructure.controllers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.common.E2ETest;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.common.DatabaseUtils;
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
public class CourseTeacherDeleteControllerTest {

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
    void should_return_404_status_code_when_delete_a_teacher_for_a_not_existing_course() throws Exception {
        mockMvc
            .perform(
                delete(String.format(
                    "/courses/%s/teachers/%s",
                    CourseIdMother.random().getValue(),
                    TeacherIdMother.random().getValue()
                ))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-not-found"));
    }

    @Test
    void should_return_404_status_code_when_delete_a_not_existing_teacher() throws Exception {
        Teacher teacher = TeacherMother.random();
        Course course = CourseMother.randomInDraftStateWithTeacher(teacher);
        teacher.addCourse(course.getId());

        courseRepository.save(course);
        teacherRepository.save(teacher);

        mockMvc
            .perform(
                delete(String.format(
                    "/courses/%s/teachers/%s",
                    course.getId().getValue(),
                    TeacherIdMother.random().getValue()
                ))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("course-teacher-not-found"));
    }

    @Test
    void should_return_200_status_code_when_delete_a_teacher() throws Exception {
        Teacher teacher = TeacherMother.random();
        Course course = CourseMother.randomInDraftStateWithTeacher(teacher);
        teacher.addCourse(course.getId());

        courseRepository.save(course);
        teacherRepository.save(teacher);

        mockMvc
            .perform(
                delete(String.format(
                    "/courses/%s/teachers/%s",
                    course.getId().getValue(),
                    teacher.getId().getValue()
                ))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
}
