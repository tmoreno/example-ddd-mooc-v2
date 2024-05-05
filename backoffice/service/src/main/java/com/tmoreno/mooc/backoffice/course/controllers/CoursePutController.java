package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.updateCourse.UpdateCourseCommand;
import com.tmoreno.mooc.backoffice.course.commands.updateCourse.UpdateCourseCommandParams;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoursePutController {

    private final UpdateCourseCommand updateCourseCommand;

    public CoursePutController(UpdateCourseCommand updateCourseCommand) {
        this.updateCourseCommand = updateCourseCommand;
    }

    @Transactional
    @PutMapping(value = "/courses/{id}")
    public ResponseEntity<String> handle(@PathVariable String id, @RequestBody UpdateCourseCommandParams params) {
        params.setCourseId(id);

        updateCourseCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
