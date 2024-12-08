package com.tmoreno.mooc.backoffice.course.commands.create;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class CreateCourseCommandParams extends CommandParams {

    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
