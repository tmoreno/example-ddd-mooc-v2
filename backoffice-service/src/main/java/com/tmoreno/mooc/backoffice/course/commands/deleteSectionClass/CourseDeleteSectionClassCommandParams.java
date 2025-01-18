package com.tmoreno.mooc.backoffice.course.commands.deleteSectionClass;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class CourseDeleteSectionClassCommandParams implements CommandParams {

    private String courseId;
    private String sectionId;
    private String sectionClassId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionClassId() {
        return sectionClassId;
    }

    public void setSectionClassId(String sectionClassId) {
        this.sectionClassId = sectionClassId;
    }
}
