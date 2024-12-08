package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.updateSectionClass.UpdateCourseSectionClassCommand;
import com.tmoreno.mooc.backoffice.course.commands.updateSectionClass.UpdateCourseSectionClassCommandParams;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseSectionClassPutController {

    private final UpdateCourseSectionClassCommand updateCourseSectionClassCommand;

    public CourseSectionClassPutController(UpdateCourseSectionClassCommand updateCourseSectionClassCommand) {
        this.updateCourseSectionClassCommand = updateCourseSectionClassCommand;
    }

    @Transactional
    @PutMapping(value = "/courses/{courseId}/sections/{sectionId}/classes/{classId}")
    public ResponseEntity<String> handle(
            @PathVariable String courseId,
            @PathVariable String sectionId,
            @PathVariable String classId,
            @RequestBody UpdateCourseSectionClassCommandParams params
    ) {
        params.setCourseId(courseId);
        params.setSectionId(sectionId);
        params.setSectionClassId(classId);

        updateCourseSectionClassCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
