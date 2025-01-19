package com.tmoreno.mooc.frontoffice.teacher.queries;

import com.tmoreno.mooc.shared.query.QueryParams;

public record FindTeacherQueryParams(
    String teacherId
) implements QueryParams {
}
