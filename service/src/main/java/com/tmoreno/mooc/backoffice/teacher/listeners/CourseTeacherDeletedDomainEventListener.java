package com.tmoreno.mooc.backoffice.teacher.listeners;

import com.tmoreno.mooc.backoffice.course.domain.events.CourseTeacherDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.teacher.handlers.CourseTeacherDeletedDomainEventHandler;
import com.tmoreno.mooc.shared.events.MoocEventListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class CourseTeacherDeletedDomainEventListener implements MoocEventListener<CourseTeacherDeletedDomainEvent> {

    private final CourseTeacherDeletedDomainEventHandler courseTeacherDeletedDomainEventHandler;

    public CourseTeacherDeletedDomainEventListener(CourseTeacherDeletedDomainEventHandler courseTeacherDeletedDomainEventHandler) {
        this.courseTeacherDeletedDomainEventHandler = courseTeacherDeletedDomainEventHandler;
    }

    @Override
    @EventListener
    public void on(CourseTeacherDeletedDomainEvent event) {
        courseTeacherDeletedDomainEventHandler.handle(event);
    }
}
