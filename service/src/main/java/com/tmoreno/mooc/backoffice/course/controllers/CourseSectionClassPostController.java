package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.addSectionClass.CourseAddSectionClassCommand;
import com.tmoreno.mooc.backoffice.course.commands.addSectionClass.CourseAddSectionClassCommandParams;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseSectionClassPostController {

    private final CourseAddSectionClassCommand courseAddSectionClassCommand;

    public CourseSectionClassPostController(CourseAddSectionClassCommand courseAddSectionClassCommand) {
        this.courseAddSectionClassCommand = courseAddSectionClassCommand;
    }

    @Transactional
    @PostMapping(value = "/courses/{courseId}/sections/{sectionId}/classes")
    public ResponseEntity<String> handle(
            @PathVariable String courseId,
            @PathVariable String sectionId,
            @RequestBody CourseAddSectionClassCommandParams params
    ) {
        params.setCourseId(courseId);
        params.setSectionId(sectionId);

        courseAddSectionClassCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
