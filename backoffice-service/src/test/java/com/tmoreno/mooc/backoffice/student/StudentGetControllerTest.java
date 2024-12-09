package com.tmoreno.mooc.backoffice.student;

import com.github.javafaker.Faker;
import com.tmoreno.mooc.backoffice.common.E2ETest;
import com.tmoreno.mooc.backoffice.common.DatabaseUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class StudentGetControllerTest {

    private static final String ANY_STUDENT_ID = UUID.randomUUID().toString();
    private static final String ANY_STUDENT_NAME = RandomStringUtils.randomAlphabetic(10, 100);
    private static final String ANY_STUDENT_EMAIL = Faker.instance().internet().emailAddress();
    private static final String OTHER_STUDENT_ID = UUID.randomUUID().toString();
    private static final String OTHER_STUDENT_NAME = RandomStringUtils.randomAlphabetic(10, 100);
    private static final String OTHER_STUDENT_EMAIL = Faker.instance().internet().emailAddress();

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
    void should_return_404_and_student_not_found_code_when_student_not_found() throws Exception {
        mockMvc
            .perform(get("/students/" + ANY_STUDENT_ID))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("student-not-found"))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Student not found: " + ANY_STUDENT_ID))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_200_and_empty_body_when_there_are_not_students() throws Exception {
        mockMvc
            .perform(get("/students"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void should_return_200_and_student_data_when_student_exists() throws Exception {
        createStudent(ANY_STUDENT_ID, ANY_STUDENT_NAME, ANY_STUDENT_EMAIL);

        mockMvc
            .perform(get("/students/" + ANY_STUDENT_ID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ANY_STUDENT_ID))
            .andExpect(jsonPath("$.name").value(ANY_STUDENT_NAME))
            .andExpect(jsonPath("$.email").value(ANY_STUDENT_EMAIL))
            .andExpect(jsonPath("$.courses").isEmpty())
            .andExpect(jsonPath("$.reviews").isEmpty());
    }

    @Test
    void should_return_200_and_all_students() throws Exception {
        createStudent(ANY_STUDENT_ID, ANY_STUDENT_NAME, ANY_STUDENT_EMAIL);
        createStudent(OTHER_STUDENT_ID, OTHER_STUDENT_NAME, OTHER_STUDENT_EMAIL);

        mockMvc
            .perform(get("/students"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[?(@.id == '" + ANY_STUDENT_ID + "')].name").value(ANY_STUDENT_NAME))
            .andExpect(jsonPath("$[?(@.id == '" + ANY_STUDENT_ID + "')].email").value(ANY_STUDENT_EMAIL))
            .andExpect(jsonPath("$[?(@.id == '" + ANY_STUDENT_ID + "')].courses.length()").value(0))
            .andExpect(jsonPath("$[?(@.id == '" + ANY_STUDENT_ID + "')].reviews.length()").value(0))
            .andExpect(jsonPath("$[?(@.id == '" + OTHER_STUDENT_ID + "')].name").value(OTHER_STUDENT_NAME))
            .andExpect(jsonPath("$[?(@.id == '" + OTHER_STUDENT_ID + "')].email").value(OTHER_STUDENT_EMAIL))
            .andExpect(jsonPath("$[?(@.id == '" + OTHER_STUDENT_ID + "')].courses.length()").value(0))
            .andExpect(jsonPath("$[?(@.id == '" + OTHER_STUDENT_ID + "')].reviews.length()").value(0));
    }

    private void createStudent(String id, String name, String email) {
        jdbcTemplate.update(
            "insert into students (id, name, email) values (?, ?, ?)",
            id,
            name,
            email
        );
    }
}
