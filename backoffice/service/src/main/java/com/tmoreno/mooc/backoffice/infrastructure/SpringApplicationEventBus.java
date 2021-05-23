package com.tmoreno.mooc.backoffice.infrastructure;

import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.shared.events.EventBus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class SpringApplicationEventBus implements EventBus {
    private final ApplicationEventPublisher publisher;

    public SpringApplicationEventBus(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(DomainEvent event) {
        publisher.publishEvent(event);
    }

    @Override
    public void publish(List<DomainEvent> events) {
        events.forEach(this::publish);
    }
}
