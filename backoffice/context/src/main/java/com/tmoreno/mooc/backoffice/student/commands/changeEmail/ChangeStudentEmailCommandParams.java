package com.tmoreno.mooc.backoffice.student.commands.changeEmail;

import com.tmoreno.mooc.shared.command.CommandParams;

public final class ChangeStudentEmailCommandParams extends CommandParams {

    private String id;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
