package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.deleteSection.CourseDeleteSectionCommand;
import com.tmoreno.mooc.backoffice.course.commands.deleteSection.CourseDeleteSectionCommandParams;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseSectionDeleteController {

    private final CourseDeleteSectionCommand courseDeleteSectionCommand;

    public CourseSectionDeleteController(CourseDeleteSectionCommand courseDeleteSectionCommand) {
        this.courseDeleteSectionCommand = courseDeleteSectionCommand;
    }

    @Transactional
    @DeleteMapping(value = "/courses/{courseId}/sections/{sectionId}")
    public ResponseEntity<String> handle(@PathVariable String courseId, @PathVariable String sectionId) {
        CourseDeleteSectionCommandParams params = new CourseDeleteSectionCommandParams();
        params.setCourseId(courseId);
        params.setSectionId(sectionId);

        courseDeleteSectionCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
