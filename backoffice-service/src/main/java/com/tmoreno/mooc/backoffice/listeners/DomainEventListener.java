package com.tmoreno.mooc.backoffice.listeners;

import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.shared.events.MoocEventListener;
import com.tmoreno.mooc.shared.handlers.StoreDomainEventHandler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class DomainEventListener implements MoocEventListener<DomainEvent> {

    private final StoreDomainEventHandler storeDomainEventHandler;

    public DomainEventListener(StoreDomainEventHandler storeDomainEventHandler) {
        this.storeDomainEventHandler = storeDomainEventHandler;
    }

    @Override
    @EventListener
    public void on(DomainEvent event) {
        storeDomainEventHandler.handle(event);
    }
}
