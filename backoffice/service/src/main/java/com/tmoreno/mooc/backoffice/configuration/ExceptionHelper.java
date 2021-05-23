package com.tmoreno.mooc.backoffice.configuration;

import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherCourseNotFoundException;
import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherExistsException;
import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.domain.ExceptionResponseBody;
import com.tmoreno.mooc.shared.domain.exceptions.InvalidDurationException;
import com.tmoreno.mooc.shared.domain.exceptions.InvalidEmailException;
import com.tmoreno.mooc.shared.domain.exceptions.InvalidIdentifierException;
import com.tmoreno.mooc.shared.domain.exceptions.InvalidPersonNameException;
import com.tmoreno.mooc.shared.domain.exceptions.MoneyValueIsNegativeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public final class ExceptionHelper {

    @ExceptionHandler(value = {
        InvalidDurationException.class,
        InvalidEmailException.class,
        InvalidIdentifierException.class,
        InvalidPersonNameException.class,
        MoneyValueIsNegativeException.class
    })
    public ResponseEntity<Object> badRequestHandler(RuntimeException e) {
        return new ResponseEntity<>(
                new ExceptionResponseBody(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

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
