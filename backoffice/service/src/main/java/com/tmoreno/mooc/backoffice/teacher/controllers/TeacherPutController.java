package com.tmoreno.mooc.backoffice.teacher.controllers;

import com.tmoreno.mooc.backoffice.teacher.commands.UpdateTeacherCommand;
import com.tmoreno.mooc.backoffice.teacher.commands.UpdateTeacherCommandParams;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherPutController {

    private final UpdateTeacherCommand updateTeacherCommand;

    public TeacherPutController(UpdateTeacherCommand updateTeacherCommand) {
        this.updateTeacherCommand = updateTeacherCommand;
    }

    @Transactional
    @PutMapping(value = "/teachers/{id}")
    public ResponseEntity<String> handle(@PathVariable String id, @RequestBody UpdateTeacherCommandParams params) {
        params.setId(id);

        updateTeacherCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
