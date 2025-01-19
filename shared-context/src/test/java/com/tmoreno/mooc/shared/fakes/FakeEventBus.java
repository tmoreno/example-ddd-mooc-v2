package com.tmoreno.mooc.shared.fakes;

import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.shared.events.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FakeEventBus implements EventBus {

    private final List<DomainEvent> events;

    public FakeEventBus() {
        this.events = new ArrayList<>();
    }

    @Override
    public void publish(DomainEvent event) {
        events.add(event);
    }

    @Override
    public void publish(List<DomainEvent> events) {
        this.events.addAll(events);
    }

    public List<DomainEvent> getEvents() {
        return events;
    }
}
