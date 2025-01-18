package com.tmoreno.mooc.backoffice.course.commands.addTeacher;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class CourseAddTeacherCommandParams implements CommandParams {

    private String courseId;
    private String teacherId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
