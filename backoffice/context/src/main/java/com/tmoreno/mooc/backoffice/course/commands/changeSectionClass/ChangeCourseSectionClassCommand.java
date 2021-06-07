package com.tmoreno.mooc.backoffice.course.commands.changeSectionClass;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClass;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassTitle;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseSectionClassNotFoundException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseSectionNotFoundException;
import com.tmoreno.mooc.shared.command.Command;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;
import com.tmoreno.mooc.shared.events.EventBus;

import java.util.Objects;

public final class ChangeCourseSectionClassCommand implements Command<ChangeCourseSectionClassCommandParams> {

    private final CourseRepository repository;
    private final EventBus eventBus;

    public ChangeCourseSectionClassCommand(CourseRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(ChangeCourseSectionClassCommandParams params) {
        CourseId courseId = new CourseId(params.getCourseId());
        SectionId sectionId = new SectionId(params.getSectionId());
        SectionClassId sectionClassId = new SectionClassId(params.getSectionClassId());
        SectionClassTitle sectionClassTitle = params.getTitle() == null ? null : new SectionClassTitle(params.getTitle());
        DurationInSeconds duration = params.getDuration() == null ? null : new DurationInSeconds(params.getDuration());

        Course course = repository.find(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        Section section = course.getSections()
                .stream()
                .filter(s -> s.getId().equals(sectionId))
                .findFirst()
                .orElseThrow(() -> new CourseSectionNotFoundException(sectionId));

        SectionClass sectionClass = section.getClasses()
                .stream()
                .filter(c -> c.getId().equals(sectionClassId))
                .findFirst()
                .orElseThrow(() -> new CourseSectionClassNotFoundException(sectionClassId));

        if (sectionClassTitle != null && !Objects.equals(sectionClassTitle, sectionClass.getTitle())) {
            course.changeSectionClassTitle(sectionId, sectionClassId, sectionClassTitle);
        }

        if (duration != null && !Objects.equals(duration, sectionClass.getDuration())) {
            course.changeSectionClassDuration(sectionId, sectionClassId, duration);
        }

        repository.save(course);

        eventBus.publish(course.pullEvents());
    }
}
