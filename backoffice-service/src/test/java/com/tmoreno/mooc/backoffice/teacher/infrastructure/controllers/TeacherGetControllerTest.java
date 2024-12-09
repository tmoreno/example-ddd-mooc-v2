package com.tmoreno.mooc.backoffice.teacher.infrastructure.controllers;

import com.github.javafaker.Faker;
import com.tmoreno.mooc.backoffice.common.E2ETest;
import com.tmoreno.mooc.backoffice.common.DatabaseUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class TeacherGetControllerTest {

    private static final String ANY_TEACHER_ID = UUID.randomUUID().toString();
    private static final String ANY_TEACHER_NAME = RandomStringUtils.randomAlphabetic(10, 100);
    private static final String ANY_TEACHER_EMAIL = Faker.instance().internet().emailAddress();
    private static final String OTHER_TEACHER_ID = UUID.randomUUID().toString();
    private static final String OTHER_TEACHER_NAME = RandomStringUtils.randomAlphabetic(10, 100);
    private static final String OTHER_TEACHER_EMAIL = Faker.instance().internet().emailAddress();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DatabaseUtils databaseUtils = new DatabaseUtils(jdbcTemplate);
        databaseUtils.initialize();
    }

    @Test
    void should_return_404_and_teacher_not_found_code_when_teacher_not_found() throws Exception {
        mockMvc
            .perform(get("/teachers/" + ANY_TEACHER_ID))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("teacher-not-found"))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Teacher not found: " + ANY_TEACHER_ID))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_200_and_empty_body_when_there_are_not_teachers() throws Exception {
        mockMvc
            .perform(get("/teachers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void should_return_200_and_teacher_data_when_teacher_exists() throws Exception {
        createTeacher(ANY_TEACHER_ID, ANY_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(get("/teachers/" + ANY_TEACHER_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ANY_TEACHER_ID))
            .andExpect(jsonPath("$.name").value(ANY_TEACHER_NAME))
            .andExpect(jsonPath("$.email").value(ANY_TEACHER_EMAIL))
            .andExpect(jsonPath("$.courses").isEmpty());
    }

    @Test
    void should_return_200_and_all_teachers() throws Exception {
        createTeacher(ANY_TEACHER_ID, ANY_TEACHER_NAME, ANY_TEACHER_EMAIL);
        createTeacher(OTHER_TEACHER_ID, OTHER_TEACHER_NAME, OTHER_TEACHER_EMAIL);

        mockMvc
            .perform(get("/teachers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[?(@.id == '" + ANY_TEACHER_ID + "')].name").value(ANY_TEACHER_NAME))
            .andExpect(jsonPath("$[?(@.id == '" + ANY_TEACHER_ID + "')].email").value(ANY_TEACHER_EMAIL))
            .andExpect(jsonPath("$[?(@.id == '" + ANY_TEACHER_ID + "')].courses.length()").value(0))
            .andExpect(jsonPath("$[?(@.id == '" + OTHER_TEACHER_ID + "')].name").value(OTHER_TEACHER_NAME))
            .andExpect(jsonPath("$[?(@.id == '" + OTHER_TEACHER_ID + "')].email").value(OTHER_TEACHER_EMAIL))
            .andExpect(jsonPath("$[?(@.id == '" + OTHER_TEACHER_ID + "')].courses.length()").value(0));
    }

    private void createTeacher(String id, String name, String email) throws Exception {
        String teacher = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(id, name, email);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher))
            .andExpect(status().isCreated());
    }
}
