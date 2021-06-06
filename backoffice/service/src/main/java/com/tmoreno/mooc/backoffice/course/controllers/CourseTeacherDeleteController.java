package com.tmoreno.mooc.backoffice.course.controllers;

import com.tmoreno.mooc.backoffice.course.commands.deleteTeacher.CourseDeleteTeacherCommand;
import com.tmoreno.mooc.backoffice.course.commands.deleteTeacher.CourseDeleteTeacherCommandParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class CourseTeacherDeleteController {

    private final CourseDeleteTeacherCommand courseDeleteTeacherCommand;

    public CourseTeacherDeleteController(CourseDeleteTeacherCommand courseDeleteTeacherCommand) {
        this.courseDeleteTeacherCommand = courseDeleteTeacherCommand;
    }

    @Transactional
    @DeleteMapping(value = "/courses/{courseId}/teachers/{teacherId}")
    public ResponseEntity<String> handle(@PathVariable String courseId, @PathVariable String teacherId) {
        CourseDeleteTeacherCommandParams params = new CourseDeleteTeacherCommandParams();
        params.setCourseId(courseId);
        params.setTeacherId(teacherId);

        courseDeleteTeacherCommand.execute(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
