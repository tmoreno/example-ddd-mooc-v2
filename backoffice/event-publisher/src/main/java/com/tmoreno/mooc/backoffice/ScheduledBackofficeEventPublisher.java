package com.tmoreno.mooc.backoffice;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledBackofficeEventPublisher {

    private final JdbcTemplate jdbcTemplate;

    public ScheduledBackofficeEventPublisher(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(fixedRate = 1000)
    public void publishEvents() {
        jdbcTemplate.queryForList(
            "select id, body " +
            "  from domain_events " +
            " where published_on is null " +
            " order by id " +
            " limit 100"
        ).forEach(event -> {
            int id = (int) event.get("id");
            String body = (String) event.get("body");

            jdbcTemplate.update("update domain_events set published_on = now() where id = ?", id);
        });
    }
}
