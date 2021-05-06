package com.tmoreno.mooc.backoffice.teachers.queries;

import com.tmoreno.mooc.backoffice.teachers.domain.Teacher;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.teachers.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.query.Query;

public final class FindTeacherQuery implements Query<FindTeacherQueryParams, FindTeacherQueryResponse> {

    private final TeacherRepository repository;

    public FindTeacherQuery(TeacherRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public FindTeacherQueryResponse execute(FindTeacherQueryParams params) {
        TeacherId id = new TeacherId(params.getTeacherId());

        Teacher teacher = repository.find(id).orElseThrow(() -> new TeacherNotFoundException(id));

        return new FindTeacherQueryResponse(teacher);
    }
}
