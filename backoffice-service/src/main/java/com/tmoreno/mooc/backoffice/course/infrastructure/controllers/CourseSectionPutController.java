package com.tmoreno.mooc.backoffice.course.infrastructure.controllers;

import com.tmoreno.mooc.backoffice.course.commands.updateSection.UpdateCourseSectionCommand;
import com.tmoreno.mooc.backoffice.course.commands.updateSection.UpdateCourseSectionCommandParams;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseSectionPutController {

    private final UpdateCourseSectionCommand updateCourseSectionCommand;

    public CourseSectionPutController(UpdateCourseSectionCommand updateCourseSectionCommand) {
        this.updateCourseSectionCommand = updateCourseSectionCommand;
    }

    @Transactional
    @PutMapping(value = "/courses/{courseId}/sections/{sectionId}")
    public ResponseEntity<String> handle(
            @PathVariable String courseId,
            @PathVariable String sectionId,
            @RequestBody UpdateCourseSectionCommandParams params
    ) {
        params.setCourseId(courseId);
        params.setSectionId(sectionId);

        updateCourseSectionCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
