package com.tmoreno.mooc.backoffice.teacher;

import com.tmoreno.mooc.backoffice.support.E2ETest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class TeacherGetControllerTest {

    private static final String ANY_TEACHER_ID = UUID.randomUUID().toString();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_404_when_teacher_not_found() throws Exception {
        mockMvc
            .perform(get("/teachers/" + ANY_TEACHER_ID))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("teacher-not-found"))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Teacher not found: " + ANY_TEACHER_ID))
            .andExpect(jsonPath("$.timestamp").exists());
    }
}
