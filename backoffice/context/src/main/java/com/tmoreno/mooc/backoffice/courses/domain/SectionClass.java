package com.tmoreno.mooc.backoffice.courses.domain;

import com.tmoreno.mooc.backoffice.shared.domain.DurationInSeconds;
import com.tmoreno.mooc.backoffice.shared.domain.Entity;

public final class SectionClass extends Entity<SectionClassId> {

    private SectionClassTitle title;
    private DurationInSeconds duration;

    public SectionClass(SectionClassId id, SectionClassTitle title, DurationInSeconds duration) {
        super(id);

        this.title = title;
        this.duration = duration;
    }

    public SectionClassTitle getTitle() {
        return title;
    }

    public void changeTitle(SectionClassTitle title) {
        this.title = title;
    }

    public DurationInSeconds getDuration() {
        return duration;
    }

    public void changeDuration(DurationInSeconds duration) {
        this.duration = duration;
    }
}
