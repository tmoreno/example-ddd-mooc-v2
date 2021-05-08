package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;

public final class CourseSectionClassNotFoundException extends RuntimeException {
    public CourseSectionClassNotFoundException(SectionClassId sectionClassId) {
        super("Course section class not found: " + sectionClassId);
    }
}
