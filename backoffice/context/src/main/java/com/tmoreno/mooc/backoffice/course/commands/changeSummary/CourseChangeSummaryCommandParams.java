package com.tmoreno.mooc.backoffice.course.commands.changeSummary;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class CourseChangeSummaryCommandParams extends CommandParams {

    private String courseId;
    private String summary;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
