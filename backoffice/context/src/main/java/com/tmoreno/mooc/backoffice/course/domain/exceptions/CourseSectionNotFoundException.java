package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class CourseSectionNotFoundException extends BaseDomainException {
    public CourseSectionNotFoundException(SectionId sectionId) {
        super(
                "course-section-not-found",
                "Course section not found: " + sectionId
        );
    }
}
