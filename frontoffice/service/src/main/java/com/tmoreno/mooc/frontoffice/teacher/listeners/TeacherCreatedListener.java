package com.tmoreno.mooc.frontoffice.teacher.listeners;

import com.tmoreno.mooc.frontoffice.teacher.domain.events.TeacherCreatedDomainEvent;
import com.tmoreno.mooc.frontoffice.teacher.handlers.TeacherCreatedDomainEventHandler;
import com.tmoreno.mooc.shared.events.MoocEventListener;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public final class TeacherCreatedListener implements MoocEventListener<TeacherCreatedDomainEvent> {

    private final TeacherCreatedDomainEventHandler teacherCreatedDomainEventHandler;

    public TeacherCreatedListener(TeacherCreatedDomainEventHandler teacherCreatedDomainEventHandler) {
        this.teacherCreatedDomainEventHandler = teacherCreatedDomainEventHandler;
    }

    @RabbitListener(
        bindings = @QueueBinding(
            exchange = @Exchange(
                value = "${infrastructure.rabbitmq.backofficeExchangeName}",
                type = "${infrastructure.rabbitmq.backofficeExchangeType}"
            ),
            value = @Queue(
                value = "frontoffice.teacher.create_on_teacher_created",
                durable = "false"
            ),
            key = "mooc.backoffice.1.teacher.created"
        ),
        messageConverter = "jsonConverter"
    )
    public void on(TeacherCreatedDomainEvent event) {
        teacherCreatedDomainEventHandler.handle(event);
    }
}
