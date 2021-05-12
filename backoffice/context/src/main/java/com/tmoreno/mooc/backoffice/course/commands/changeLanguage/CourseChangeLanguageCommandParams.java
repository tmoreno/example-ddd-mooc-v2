package com.tmoreno.mooc.backoffice.course.commands.changeLanguage;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class CourseChangeLanguageCommandParams extends CommandParams {

    private String courseId;
    private String language;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
