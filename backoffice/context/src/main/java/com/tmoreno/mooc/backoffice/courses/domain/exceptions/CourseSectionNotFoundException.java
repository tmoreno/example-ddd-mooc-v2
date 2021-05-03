package com.tmoreno.mooc.backoffice.courses.domain.exceptions;

import com.tmoreno.mooc.backoffice.courses.domain.SectionId;

public final class CourseSectionNotFoundException extends RuntimeException {
    public CourseSectionNotFoundException(SectionId sectionId) {
        super("Course section not found: " + sectionId);
    }
}
