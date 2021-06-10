package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.changeSection.ChangeCourseSectionCommand;
import com.tmoreno.mooc.backoffice.course.commands.changeSection.ChangeCourseSectionCommandParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class CourseSectionPutController {

    private final ChangeCourseSectionCommand changeCourseSectionCommand;

    public CourseSectionPutController(ChangeCourseSectionCommand changeCourseSectionCommand) {
        this.changeCourseSectionCommand = changeCourseSectionCommand;
    }

    @Transactional
    @PutMapping(value = "/courses/{courseId}/sections/{sectionId}")
    public ResponseEntity<String> handle(
            @PathVariable String courseId,
            @PathVariable String sectionId,
            @RequestBody ChangeCourseSectionCommandParams params
    ) {
        params.setCourseId(courseId);
        params.setSectionId(sectionId);

        changeCourseSectionCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
