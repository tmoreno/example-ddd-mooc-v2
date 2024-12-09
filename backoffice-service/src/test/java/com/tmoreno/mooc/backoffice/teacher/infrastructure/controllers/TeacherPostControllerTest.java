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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class TeacherPostControllerTest {

    private static final String ANY_TEACHER_ID = UUID.randomUUID().toString();
    private static final String ANY_TEACHER_NAME = RandomStringUtils.randomAlphabetic(10, 100);
    private static final String ANY_TEACHER_EMAIL = Faker.instance().internet().emailAddress();
    private static final String INVALID_TEACHER_ID = RandomStringUtils.randomAlphabetic(1, 50);
    private static final String BIG_TEACHER_NAME = RandomStringUtils.randomAlphabetic(101, 120);
    private static final String SMALL_TEACHER_NAME = RandomStringUtils.randomAlphabetic(1, 10);
    private static final String INVALID_TEACHER_EMAIL = RandomStringUtils.randomAlphabetic(1, 50);
    private static final String OTHER_TEACHER_ID = UUID.randomUUID().toString();
    private static final String OTHER_TEACHER_EMAIL = Faker.instance().internet().emailAddress();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        DatabaseUtils databaseUtils = new DatabaseUtils(jdbcTemplate);
        databaseUtils.initialize();
    }

    @Test
    void should_return_400_status_code_and_invalid_identifier_when_teacher_identifier_is_not_valid() throws Exception {
        String teacher = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(INVALID_TEACHER_ID, ANY_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("invalid-identifier"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid identifier value: " + INVALID_TEACHER_ID))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_400_status_code_and_invalid_person_name_when_teacher_name_is_blank() throws Exception {
        String teacher = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_ID, "", ANY_TEACHER_EMAIL);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("invalid-person-name"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid person name: Person name can't be blank"))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_400_status_code_and_invalid_person_name_when_teacher_name_is_more_than_100_chars() throws Exception {
        String teacher = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_ID, BIG_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("invalid-person-name"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid person name: Person name length is more than 100"))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_400_status_code_and_invalid_person_name_when_teacher_name_is_less_than_10_chars() throws Exception {
        String teacher = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_ID, SMALL_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("invalid-person-name"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid person name: Person name length is less than 10"))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_400_status_code_and_invalid_email_when_teacher_email_is_not_valid() throws Exception {
        String teacher = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_ID, ANY_TEACHER_NAME, INVALID_TEACHER_EMAIL);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("invalid-email"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid email value: " + INVALID_TEACHER_EMAIL))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_201_status_code_when_create_a_teacher() throws Exception {
        String teacher = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_ID, ANY_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void should_return_412_status_code_and_teacher_exists_when_create_two_teachers_with_same_id() throws Exception {
        String teacher = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_ID, ANY_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").doesNotExist());

        String teacher2 = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_ID, ANY_TEACHER_NAME, OTHER_TEACHER_EMAIL);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher2))
            .andExpect(status().isPreconditionFailed())
            .andExpect(jsonPath("$.code").value("teacher-exists"))
            .andExpect(jsonPath("$.status").value(412))
            .andExpect(jsonPath("$.message").value(String.format("A teacher with this id: %s or with this email: %s already exists", ANY_TEACHER_ID, OTHER_TEACHER_EMAIL)))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_412_status_code_and_teacher_exists_when_create_two_teachers_with_same_email() throws Exception {
        String teacher = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_ID, ANY_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").doesNotExist());

        String teacher2 = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(OTHER_TEACHER_ID, ANY_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher2))
            .andExpect(status().isPreconditionFailed())
            .andExpect(jsonPath("$.code").value("teacher-exists"))
            .andExpect(jsonPath("$.status").value(412))
            .andExpect(jsonPath("$.message").value(String.format("A teacher with this id: %s or with this email: %s already exists", OTHER_TEACHER_ID, ANY_TEACHER_EMAIL)))
            .andExpect(jsonPath("$.timestamp").exists());
    }
}
