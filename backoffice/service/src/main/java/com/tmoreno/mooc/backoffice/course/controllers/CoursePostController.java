package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.create.CreateCourseCommand;
import com.tmoreno.mooc.backoffice.course.commands.create.CreateCourseCommandParams;
import com.tmoreno.mooc.backoffice.course.commands.discard.DiscardCourseCommand;
import com.tmoreno.mooc.backoffice.course.commands.discard.DiscardCourseCommandParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class CoursePostController {

    private final CreateCourseCommand createCourseCommand;
    private final DiscardCourseCommand discardCourseCommand;

    public CoursePostController(CreateCourseCommand createCourseCommand, DiscardCourseCommand discardCourseCommand) {
        this.createCourseCommand = createCourseCommand;
        this.discardCourseCommand = discardCourseCommand;
    }

    @Transactional
    @PostMapping(value = "/courses")
    public ResponseEntity<String> create(@RequestBody CreateCourseCommandParams params) {
        createCourseCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping(value = "/courses/{id}/discard")
    public ResponseEntity<String> discard(@PathVariable String id) {
        DiscardCourseCommandParams params = new DiscardCourseCommandParams();
        params.setCourseId(id);

        discardCourseCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
