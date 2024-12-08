package com.tmoreno.mooc.frontoffice.teacher.listeners;

import com.tmoreno.mooc.frontoffice.teacher.domain.events.TeacherEmailChangedDomainEvent;
import com.tmoreno.mooc.frontoffice.teacher.handlers.TeacherEmailChangedDomainEventHandler;
import com.tmoreno.mooc.shared.events.MoocEventListener;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public final class TeacherEmailChangedListener implements MoocEventListener<TeacherEmailChangedDomainEvent> {

    private final TeacherEmailChangedDomainEventHandler teacherEmailChangedDomainEventHandler;

    public TeacherEmailChangedListener(TeacherEmailChangedDomainEventHandler teacherEmailChangedDomainEventHandler) {
        this.teacherEmailChangedDomainEventHandler = teacherEmailChangedDomainEventHandler;
    }

    @Override
    @RabbitListener(
        bindings = @QueueBinding(
            exchange = @Exchange(
                value = "${infrastructure.rabbitmq.backofficeExchangeName}",
                type = "${infrastructure.rabbitmq.backofficeExchangeType}"
            ),
            value = @Queue(
                value = "frontoffice.teacher.change_email_on_teacher_email_changed",
                durable = "false"
            ),
            key = "mooc.backoffice.1.teacher.email.changed"
        ),
        messageConverter = "jsonConverter"
    )
    public void on(TeacherEmailChangedDomainEvent event) {
        teacherEmailChangedDomainEventHandler.handle(event);
    }
}
