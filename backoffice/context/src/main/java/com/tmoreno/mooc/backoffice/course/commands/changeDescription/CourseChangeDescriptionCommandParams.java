package com.tmoreno.mooc.backoffice.course.commands.changeDescription;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class CourseChangeDescriptionCommandParams extends CommandParams {

    private String courseId;
    private String description;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
