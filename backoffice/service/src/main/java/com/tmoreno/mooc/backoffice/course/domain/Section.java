package com.tmoreno.mooc.backoffice.course.domain;

import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseSectionClassNotFoundException;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;
import com.tmoreno.mooc.shared.domain.Entity;

import java.util.ArrayList;
import java.util.List;

public final class Section extends Entity<SectionId> {

    private SectionTitle title;
    private final List<SectionClass> classes;

    public Section(SectionId id, SectionTitle title) {
        super(id);

        this.title = title;
        this.classes = new ArrayList<>();
    }

    public Section(SectionId id, SectionTitle title, List<SectionClass> classes) {
        super(id);

        this.title = title;
        this.classes = classes;
    }

    public SectionTitle getTitle() {
        return title;
    }

    public void changeTitle(SectionTitle title) {
        this.title = title;
    }

    public List<SectionClass> getClasses() {
        return List.copyOf(classes);
    }

    public void addClass(SectionClassId sectionClassId, SectionClassTitle title, DurationInSeconds duration) {
        SectionClass sectionClass = new SectionClass(sectionClassId, title, duration);
        classes.add(sectionClass);
    }

    public void deleteClass(SectionClassId sectionClassId) {
        boolean removed = classes.removeIf(sectionClass -> sectionClass.getId().equals(sectionClassId));

        if (!removed) {
            throw new CourseSectionClassNotFoundException(sectionClassId);
        }
    }
}
