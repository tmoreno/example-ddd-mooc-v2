package com.tmoreno.mooc.backoffice.teacher.controllers;

import com.tmoreno.mooc.backoffice.teacher.commands.CreateTeacherCommand;
import com.tmoreno.mooc.backoffice.teacher.commands.CreateTeacherCommandParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class TeacherPostController {

    private final CreateTeacherCommand createTeacherCommand;

    public TeacherPostController(CreateTeacherCommand createTeacherCommand) {
        this.createTeacherCommand = createTeacherCommand;
    }

    @Transactional
    @PostMapping(value = "/teachers")
    public ResponseEntity<String> handle(@RequestBody CreateTeacherCommandParams params) {
        createTeacherCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
