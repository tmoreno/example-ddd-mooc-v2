package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.changeCourse.ChangeCourseCommand;
import com.tmoreno.mooc.backoffice.course.commands.changeCourse.ChangeCourseCommandParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class CoursePutController {

    private final ChangeCourseCommand changeCourseCommand;

    public CoursePutController(ChangeCourseCommand changeCourseCommand) {
        this.changeCourseCommand = changeCourseCommand;
    }

    @Transactional
    @PutMapping(value = "/courses/{id}")
    public ResponseEntity<String> handle(@PathVariable String id, @RequestBody ChangeCourseCommandParams params) {
        params.setCourseId(id);

        changeCourseCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
