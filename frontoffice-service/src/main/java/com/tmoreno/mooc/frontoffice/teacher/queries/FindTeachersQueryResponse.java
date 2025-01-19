package com.tmoreno.mooc.frontoffice.teacher.queries;

import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.List;

public record FindTeachersQueryResponse(
    List<FindTeacherQueryResponse> teachers
) implements QueryResponse {
}
