package com.tmoreno.mooc.backoffice.course.commands.changeTitle;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class CourseChangeTitleCommandParams extends CommandParams {

    private String courseId;
    private String title;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
