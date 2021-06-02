package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.create.CreateCourseCommand;
import com.tmoreno.mooc.backoffice.course.commands.create.CreateCourseCommandParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class CoursePostController {

    private final CreateCourseCommand createCourseCommand;

    public CoursePostController(CreateCourseCommand createCourseCommand) {
        this.createCourseCommand = createCourseCommand;
    }

    @Transactional
    @PostMapping(value = "/courses")
    public ResponseEntity<String> handle(@RequestBody CreateCourseCommandParams params) {
        createCourseCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
