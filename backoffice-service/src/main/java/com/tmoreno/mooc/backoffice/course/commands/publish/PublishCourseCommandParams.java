package com.tmoreno.mooc.backoffice.course.commands.publish;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class PublishCourseCommandParams implements CommandParams {

    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
