package com.tmoreno.mooc.backoffice.course.commands.deleteSection;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class CourseDeleteSectionCommandParams extends CommandParams {

    private String courseId;
    private String sectionId;

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
}
