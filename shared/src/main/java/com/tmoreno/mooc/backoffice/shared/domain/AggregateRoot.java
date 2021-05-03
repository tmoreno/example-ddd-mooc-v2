package com.tmoreno.mooc.backoffice.shared.domain;

import com.tmoreno.mooc.backoffice.shared.events.DomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AggregateRoot<I extends Identifier> {
    protected final I id;

    private final List<DomainEvent> domainEvents;

    public AggregateRoot(I id) {
        this.id = id;
        domainEvents = new ArrayList<>();
    }

    public final I getId() {
        return id;
    }

    public final List<DomainEvent> pullEvents() {
        List<DomainEvent> events = List.copyOf(domainEvents);

        domainEvents.clear();

        return events;
    }

    protected final void recordEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregateRoot that = (AggregateRoot) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
