package com.tmoreno.mooc.backoffice.common;

import org.springframework.jdbc.core.JdbcTemplate;

public final class DatabaseUtils {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseUtils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void initialize() {
        jdbcTemplate.update("delete from domain_events");
        jdbcTemplate.update("delete from teachers");
        jdbcTemplate.update("delete from teacher_courses");
        jdbcTemplate.update("delete from students");
        jdbcTemplate.update("delete from student_courses");
        jdbcTemplate.update("delete from student_reviews");
        jdbcTemplate.update("delete from reviews");
        jdbcTemplate.update("delete from courses");
        jdbcTemplate.update("delete from course_sections");
        jdbcTemplate.update("delete from course_section_classes");
        jdbcTemplate.update("delete from course_reviews");
        jdbcTemplate.update("delete from course_students");
        jdbcTemplate.update("delete from course_teachers");
    }
}
