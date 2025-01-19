package com.tmoreno.mooc.shared.events;

import com.tmoreno.mooc.shared.domain.Identifier;

import java.time.Instant;

public abstract class DomainEvent {
    private Identifier eventId;
    protected String eventName;
    protected int version;
    private Instant occurredOn;

    public DomainEvent() {
        this.eventId = new Identifier();
        this.occurredOn = Instant.now();
    }

    public String getEventId() {
        return eventId.getValue();
    }

    public void setEventId(String eventId) {
        this.eventId = new Identifier(eventId);
    }

    public abstract String getEventName();

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public abstract int getVersion();

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOccurredOn() {
        return occurredOn.toString();
    }

    public void setOccurredOn(String occurredOn) {
        this.occurredOn = Instant.parse(occurredOn);
    }
}
