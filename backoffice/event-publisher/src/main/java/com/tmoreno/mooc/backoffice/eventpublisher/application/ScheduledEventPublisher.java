package com.tmoreno.mooc.backoffice.eventpublisher.application;

import com.tmoreno.mooc.backoffice.eventpublisher.infrastructure.RabbitMqPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledEventPublisher.class);

    private final JdbcTemplate jdbcTemplate;
    private final RabbitMqPublisher rabbitMqPublisher;

    public ScheduledEventPublisher(JdbcTemplate jdbcTemplate, RabbitMqPublisher rabbitMqPublisher) {
        this.jdbcTemplate = jdbcTemplate;
        this.rabbitMqPublisher = rabbitMqPublisher;
    }

    @Scheduled(fixedRate = 1000)
    public void publishEvents() {
        jdbcTemplate.queryForList(
            "select id, name, version, body " +
            "  from domain_events " +
            " where published_on is null " +
            " order by id " +
            " limit 100"
        ).forEach(event -> {
            try {
                int id = (int) event.get("id");
                String name = (String) event.get("name");
                int version = (int) event.get("version");
                String body = (String) event.get("body");

                rabbitMqPublisher.publish(name, version, body);

                jdbcTemplate.update("update domain_events set published_on = now() where id = ?", id);
            } catch (Throwable e) {
                logger.error("Unable to publish event", e);
            }
        });
    }
}
