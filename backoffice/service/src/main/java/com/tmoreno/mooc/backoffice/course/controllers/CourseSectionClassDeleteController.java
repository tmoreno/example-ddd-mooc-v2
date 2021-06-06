package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.deleteSectionClass.CourseDeleteSectionClassCommand;
import com.tmoreno.mooc.backoffice.course.commands.deleteSectionClass.CourseDeleteSectionClassCommandParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class CourseSectionClassDeleteController {

    private final CourseDeleteSectionClassCommand courseDeleteSectionClassCommand;

    public CourseSectionClassDeleteController(CourseDeleteSectionClassCommand courseDeleteSectionClassCommand) {
        this.courseDeleteSectionClassCommand = courseDeleteSectionClassCommand;
    }

    @Transactional
    @DeleteMapping(value = "/courses/{courseId}/sections/{sectionId}/classes/{sectionClassId}")
    public ResponseEntity<String> handle(
            @PathVariable String courseId,
            @PathVariable String sectionId,
            @PathVariable String sectionClassId
    ) {
        CourseDeleteSectionClassCommandParams params = new CourseDeleteSectionClassCommandParams();
        params.setCourseId(courseId);
        params.setSectionId(sectionId);
        params.setSectionClassId(sectionClassId);

        courseDeleteSectionClassCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
