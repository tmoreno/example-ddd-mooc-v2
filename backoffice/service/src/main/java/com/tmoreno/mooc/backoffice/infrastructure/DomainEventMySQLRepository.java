package com.tmoreno.mooc.backoffice.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmoreno.mooc.shared.domain.DomainEventRepository;
import com.tmoreno.mooc.shared.events.DomainEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;

@Repository
public class DomainEventMySQLRepository implements DomainEventRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public DomainEventMySQLRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void store(DomainEvent event) {
        try {
            jdbcTemplate.update(
                "insert into domain_events (event_id, name, version, body, occurred_on) values(?, ?, ?, ?, ?)",
                event.getEventId(),
                event.getEventName(),
                event.getVersion(),
                objectMapper.writeValueAsString(event),
                Timestamp.from(Instant.parse(event.getOccurredOn()))
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
