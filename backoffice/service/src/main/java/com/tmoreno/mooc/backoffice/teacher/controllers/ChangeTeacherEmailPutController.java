package com.tmoreno.mooc.backoffice.teacher.controllers;

import com.tmoreno.mooc.backoffice.teacher.commands.changeEmail.ChangeTeacherEmailCommand;
import com.tmoreno.mooc.backoffice.teacher.commands.changeEmail.ChangeTeacherEmailCommandParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class ChangeTeacherEmailPutController {

    private final ChangeTeacherEmailCommand changeTeacherEmailCommand;

    public ChangeTeacherEmailPutController(ChangeTeacherEmailCommand changeTeacherEmailCommand) {
        this.changeTeacherEmailCommand = changeTeacherEmailCommand;
    }

    @PutMapping(value = "/teachers/{id}")
    public ResponseEntity<String> handle(@PathVariable String id, @RequestBody ChangeTeacherEmailCommandParams params) {
        params.setId(id);

        changeTeacherEmailCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
