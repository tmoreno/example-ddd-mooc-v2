package com.tmoreno.mooc.backoffice.teacher.infrastructure.listeners;

import com.tmoreno.mooc.backoffice.course.domain.events.CourseTeacherAddedDomainEvent;
import com.tmoreno.mooc.backoffice.teacher.handlers.CourseTeacherAddedDomainEventHandler;
import com.tmoreno.mooc.shared.events.MoocEventListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class CourseTeacherAddedEventListener implements MoocEventListener<CourseTeacherAddedDomainEvent> {

    private final CourseTeacherAddedDomainEventHandler courseTeacherAddedDomainEventHandler;

    public CourseTeacherAddedEventListener(CourseTeacherAddedDomainEventHandler courseTeacherAddedDomainEventHandler) {
        this.courseTeacherAddedDomainEventHandler = courseTeacherAddedDomainEventHandler;
    }

    @Override
    @EventListener
    public void on(CourseTeacherAddedDomainEvent event) {
        courseTeacherAddedDomainEventHandler.handle(event);
    }
}
