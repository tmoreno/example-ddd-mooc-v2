package com.tmoreno.mooc.frontoffice.teacher.controllers;

import com.tmoreno.mooc.frontoffice.common.controllers.ErrorResponse;
import com.tmoreno.mooc.frontoffice.teacher.queries.FindTeacherQuery;
import com.tmoreno.mooc.frontoffice.teacher.queries.FindTeacherQueryParams;
import com.tmoreno.mooc.frontoffice.teacher.queries.FindTeacherQueryResponse;
import com.tmoreno.mooc.frontoffice.teacher.queries.FindTeachersQuery;
import com.tmoreno.mooc.shared.query.VoidQueryParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "Teacher")
public class TeacherGetController {

    private final FindTeacherQuery findTeacherQuery;
    private final FindTeachersQuery findTeachersQuery;

    public TeacherGetController(FindTeacherQuery findTeacherQuery, FindTeachersQuery findTeachersQuery) {
        this.findTeacherQuery = findTeacherQuery;
        this.findTeachersQuery = findTeachersQuery;
    }

    @Operation(
        summary = "Get all teachers",
        description = "Return a list of teachers",
        operationId = "getTeachers",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Returns the teachers"
            )
        }
    )
    @GetMapping(value = "/teachers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeacherResponseBody> handle() {
        return findTeachersQuery.execute(new VoidQueryParams())
            .teachers()
            .stream()
            .map(TeacherResponseBody::from)
            .toList();
    }

    @Operation(
        summary = "Get teacher",
        description = "Return a teacher by a identifier",
        operationId = "getTeacher",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Returns the teacher"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "When teacher does not exists",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @GetMapping(value = "/teachers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TeacherResponseBody handle(@PathVariable String id) {
        FindTeacherQueryParams params = new FindTeacherQueryParams(id);

        return TeacherResponseBody.from(findTeacherQuery.execute(params));
    }

    public record TeacherResponseBody(
        @Schema(description = "Teacher identifier", example = "8b6be68a-02da-4356-b3f3-7186a6397f43")
        String id,

        @Schema(description = "Teacher name", example = "Peter")
        String name,

        @Schema(description = "Teacher email", example = "peter@mail.com")
        String email,

        @ArraySchema(schema = @Schema(description = "Teacher courses", example = "a930037a-7639-4c09-9015-ab7fe21a1198"))
        Set<String> courses
    ) {
        public static TeacherResponseBody from(FindTeacherQueryResponse response) {
            return new TeacherResponseBody(response.id(), response.name(), response.email(), response.courses());
        }
    }
}
