package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.shared.domain.exceptions.BaseDomainException;

public final class CourseSectionClassNotFoundException extends BaseDomainException {
    public CourseSectionClassNotFoundException(SectionClassId sectionClassId) {
        super(
                "course-section-class-not-found",
                "Course section class not found: " + sectionClassId
        );
    }
}
