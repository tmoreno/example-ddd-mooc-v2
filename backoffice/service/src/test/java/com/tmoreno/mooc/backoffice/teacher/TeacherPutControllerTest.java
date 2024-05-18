package com.tmoreno.mooc.backoffice.teacher;

import com.github.javafaker.Faker;
import com.tmoreno.mooc.backoffice.support.E2ETest;
import com.tmoreno.mooc.backoffice.utils.DatabaseUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class TeacherPutControllerTest {

    private static final String ANY_TEACHER_ID = UUID.randomUUID().toString();
    private static final String ANY_TEACHER_NAME = RandomStringUtils.randomAlphabetic(10, 100);
    private static final String ANY_TEACHER_EMAIL = Faker.instance().internet().emailAddress();
    private static final String INVALID_TEACHER_ID = RandomStringUtils.randomAlphabetic(1, 50);
    private static final String BIG_TEACHER_NAME = RandomStringUtils.randomAlphabetic(101, 120);
    private static final String SMALL_TEACHER_NAME = RandomStringUtils.randomAlphabetic(1, 10);
    private static final String INVALID_TEACHER_EMAIL = RandomStringUtils.randomAlphabetic(1, 50);
    private static final String OTHER_TEACHER_NAME = RandomStringUtils.randomAlphabetic(10, 100);
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
        String content = """
            {
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(put("/teachers/" + INVALID_TEACHER_ID).contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("invalid-identifier"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid identifier value: " + INVALID_TEACHER_ID))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_400_status_code_and_invalid_person_name_when_teacher_name_is_blank() throws Exception {
        String content = """
            {
                "name": "%s",
                "email": "%s"
            }
            """.formatted("", ANY_TEACHER_EMAIL);

        mockMvc
            .perform(put("/teachers/" + ANY_TEACHER_ID).contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("invalid-person-name"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid person name: Person name can't be blank"))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_400_status_code_and_invalid_person_name_when_teacher_name_is_more_than_100_chars() throws Exception {
        String content = """
            {
                "name": "%s",
                "email": "%s"
            }
            """.formatted(BIG_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(put("/teachers/" + ANY_TEACHER_ID).contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("invalid-person-name"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid person name: Person name length is more than 100"))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_400_status_code_and_invalid_person_name_when_teacher_name_is_less_than_10_chars() throws Exception {
        String content = """
            {
                "name": "%s",
                "email": "%s"
            }
            """.formatted(SMALL_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(put("/teachers/" + ANY_TEACHER_ID).contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("invalid-person-name"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid person name: Person name length is less than 10"))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_400_status_code_and_invalid_email_when_teacher_email_is_not_valid() throws Exception {
        String content = """
            {
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_NAME, INVALID_TEACHER_EMAIL);

        mockMvc
            .perform(put("/teachers/" + ANY_TEACHER_ID).contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("invalid-email"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Invalid email value: " + INVALID_TEACHER_EMAIL))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_404_status_code_when_teacher_not_exists() throws Exception {
        String content = """
            {
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(put("/teachers/" + ANY_TEACHER_ID).contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("teacher-not-found"))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Teacher not found: " + ANY_TEACHER_ID))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void should_return_200_status_code_and_change_teacher_data() throws Exception {
        String teacher = """
            {
                "id": "%s",
                "name": "%s",
                "email": "%s"
            }
            """.formatted(ANY_TEACHER_ID, ANY_TEACHER_NAME, ANY_TEACHER_EMAIL);

        mockMvc
            .perform(post("/teachers").contentType(MediaType.APPLICATION_JSON).content(teacher))
            .andExpect(status().isCreated());

        String content = """
            {
                "name": "%s",
                "email": "%s"
            }
            """.formatted(OTHER_TEACHER_NAME, OTHER_TEACHER_EMAIL);

        mockMvc
            .perform(put("/teachers/" + ANY_TEACHER_ID).contentType(MediaType.APPLICATION_JSON).content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").doesNotExist());
    }
}
