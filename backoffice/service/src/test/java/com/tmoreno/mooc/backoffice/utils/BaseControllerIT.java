package com.tmoreno.mooc.backoffice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmoreno.mooc.shared.domain.DomainEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BaseControllerIT {

    protected String url;

    @SpyBean
    protected DomainEventRepository domainEventRepository;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public final void setUpBaseTest() {
        url = "http://localhost:" + serverPort;

        mongoTemplate.getDb().drop();
        jdbcTemplate.update("delete from domain_events");
    }

    public final ResponseEntity<String> get() {
        return restTemplate.getForEntity(url, String.class);
    }

    public final ResponseEntity<String> get(String id) {
        return restTemplate.getForEntity(url + "/" + id, String.class);
    }

    public final ResponseEntity<String> post(Map<String, Object> request) {
        return restTemplate.postForEntity(url, request, String.class);
    }

    public final ResponseEntity<String> put(String id, Map<String, Object> request) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request);

        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, entity, String.class);
    }

    public final JsonNode toJson(String content) throws JsonProcessingException {
        return objectMapper.readTree(content);
    }
}
