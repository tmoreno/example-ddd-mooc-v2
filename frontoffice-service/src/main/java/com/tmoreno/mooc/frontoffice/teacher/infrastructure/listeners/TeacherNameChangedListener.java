package com.tmoreno.mooc.frontoffice.teacher.infrastructure.listeners;

import com.tmoreno.mooc.frontoffice.teacher.domain.events.TeacherNameChangedDomainEvent;
import com.tmoreno.mooc.frontoffice.teacher.handlers.TeacherNameChangedDomainEventHandler;
import com.tmoreno.mooc.shared.events.MoocEventListener;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public final class TeacherNameChangedListener implements MoocEventListener<TeacherNameChangedDomainEvent> {

    private final TeacherNameChangedDomainEventHandler teacherNameChangedDomainEventHandler;

    public TeacherNameChangedListener(TeacherNameChangedDomainEventHandler teacherNameChangedDomainEventHandler) {
        this.teacherNameChangedDomainEventHandler = teacherNameChangedDomainEventHandler;
    }

    @Override
    @RabbitListener(
        bindings = @QueueBinding(
            exchange = @Exchange(
                value = "${infrastructure.rabbitmq.backofficeExchangeName}",
                type = "${infrastructure.rabbitmq.backofficeExchangeType}"
            ),
            value = @Queue(
                value = "frontoffice.teacher.change_name_on_teacher_name_changed",
                durable = "false"
            ),
            key = "mooc.backoffice.1.teacher.name.changed"
        ),
        messageConverter = "jsonConverter"
    )
    public void on(TeacherNameChangedDomainEvent event) {
        teacherNameChangedDomainEventHandler.handle(event);
    }
}
