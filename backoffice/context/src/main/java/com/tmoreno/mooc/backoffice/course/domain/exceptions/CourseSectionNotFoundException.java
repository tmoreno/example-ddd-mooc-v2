package com.tmoreno.mooc.backoffice.course.domain.exceptions;

import com.tmoreno.mooc.backoffice.course.domain.SectionId;

public final class CourseSectionNotFoundException extends RuntimeException {
    public CourseSectionNotFoundException(SectionId sectionId) {
        super("Course section not found: " + sectionId);
    }
}
