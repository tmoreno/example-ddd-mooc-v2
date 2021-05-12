package com.tmoreno.mooc.backoffice.course.commands.changeImageUrl;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class CourseChangeImageUrlCommandParams extends CommandParams {

    private String courseId;
    private String imageUrl;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
