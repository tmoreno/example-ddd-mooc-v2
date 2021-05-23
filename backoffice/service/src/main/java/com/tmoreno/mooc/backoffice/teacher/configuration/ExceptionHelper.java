package com.tmoreno.mooc.backoffice.teacher.configuration;

import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherCourseNotFoundException;
import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherExistsException;
import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.domain.ExceptionResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public final class ExceptionHelper {

    @ExceptionHandler(value = {TeacherCourseNotFoundException.class, TeacherNotFoundException.class})
    public ResponseEntity<Object> notFoundHandler(RuntimeException e) {
        return new ResponseEntity<>(
                new ExceptionResponseBody(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(TeacherExistsException.class)
    public ResponseEntity<Object> preconditionFailedHandler(RuntimeException e) {
        return new ResponseEntity<>(
                new ExceptionResponseBody(HttpStatus.PRECONDITION_FAILED.value(), e.getMessage()),
                HttpStatus.PRECONDITION_FAILED
        );
    }
}
