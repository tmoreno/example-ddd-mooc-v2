package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.changeSectionClass.ChangeCourseSectionClassCommand;
import com.tmoreno.mooc.backoffice.course.commands.changeSectionClass.ChangeCourseSectionClassCommandParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class CourseSectionClassPutController {

    private final ChangeCourseSectionClassCommand changeCourseSectionClassCommand;

    public CourseSectionClassPutController(ChangeCourseSectionClassCommand changeCourseSectionClassCommand) {
        this.changeCourseSectionClassCommand = changeCourseSectionClassCommand;
    }

    @Transactional
    @PutMapping(value = "/courses/{courseId}/sections/{sectionId}/classes/{classId}")
    public ResponseEntity<String> handle(
            @PathVariable String courseId,
            @PathVariable String sectionId,
            @PathVariable String classId,
            @RequestBody ChangeCourseSectionClassCommandParams params
    ) {
        params.setCourseId(courseId);
        params.setSectionId(sectionId);
        params.setSectionClassId(classId);

        changeCourseSectionClassCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
