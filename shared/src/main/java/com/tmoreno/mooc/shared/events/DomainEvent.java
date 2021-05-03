package com.tmoreno.mooc.shared.events;

import com.tmoreno.mooc.shared.domain.Identifier;

import java.time.Instant;

public abstract class DomainEvent {
    private final Identifier aggregateId;
    private final Identifier eventId;
    private final Instant occurredOn;

    public DomainEvent(Identifier aggregateId) {
        this.aggregateId = aggregateId;
        this.eventId = new Identifier();
        this.occurredOn = Instant.now();
    }

    public abstract String getEventName();

    public abstract int getVersion();

    public String getAggregateId() {
        return aggregateId.getValue();
    }

    public String getEventId() {
        return eventId.getValue();
    }

    public String getOccurredOn() {
        return occurredOn.toString();
    }
}
