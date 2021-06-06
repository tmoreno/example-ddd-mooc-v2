package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.addTeacher.CourseAddTeacherCommand;
import com.tmoreno.mooc.backoffice.course.commands.addTeacher.CourseAddTeacherCommandParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class CourseTeacherPostController {

    private final CourseAddTeacherCommand courseAddTeacherCommand;

    public CourseTeacherPostController(CourseAddTeacherCommand courseAddTeacherCommand) {
        this.courseAddTeacherCommand = courseAddTeacherCommand;
    }

    @Transactional
    @PostMapping(value = "/courses/{id}/teachers")
    public ResponseEntity<String> handle(@PathVariable String id, @RequestBody CourseAddTeacherCommandParams params) {
        params.setCourseId(id);

        courseAddTeacherCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
