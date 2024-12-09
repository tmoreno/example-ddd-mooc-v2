package com.tmoreno.mooc.backoffice.course.infrastructure.controllers;

import com.tmoreno.mooc.backoffice.course.commands.addSection.CourseAddSectionCommand;
import com.tmoreno.mooc.backoffice.course.commands.addSection.CourseAddSectionCommandParams;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseSectionPostController {

    private final CourseAddSectionCommand courseAddSectionCommand;

    public CourseSectionPostController(CourseAddSectionCommand courseAddSectionCommand) {
        this.courseAddSectionCommand = courseAddSectionCommand;
    }

    @Transactional
    @PostMapping(value = "/courses/{id}/sections")
    public ResponseEntity<String> handle(@PathVariable String id, @RequestBody CourseAddSectionCommandParams params) {
        params.setCourseId(id);

        courseAddSectionCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
