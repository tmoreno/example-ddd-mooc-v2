package com.tmoreno.mooc.backoffice.courses.domain.exceptions;

import com.tmoreno.mooc.backoffice.courses.domain.SectionClassId;

public final class CourseSectionClassNotFoundException extends RuntimeException {
    public CourseSectionClassNotFoundException(SectionClassId sectionClassId) {
        super("Course section class not found: " + sectionClassId);
    }
}
