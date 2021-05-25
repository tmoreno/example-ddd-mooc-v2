package com.tmoreno.mooc.backoffice;

import com.tmoreno.mooc.shared.domain.DomainEventRepository;
import com.tmoreno.mooc.shared.events.DomainEvent;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BaseControllerIT {

    @LocalServerPort
    protected int serverPort;

    @Autowired
    protected TestRestTemplate restTemplate;

    @MockBean
    private DomainEventRepository domainEventRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() {
        mongoTemplate.getDb().drop();
    }

    protected <E extends DomainEvent> void verifyDomainEventStored(Class<E> event) {
        verify(domainEventRepository).store(any(event));
    }
}
