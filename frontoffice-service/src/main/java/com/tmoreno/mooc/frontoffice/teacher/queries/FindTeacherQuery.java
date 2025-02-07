package com.tmoreno.mooc.frontoffice.teacher.queries;

import com.tmoreno.mooc.frontoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.frontoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.frontoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.frontoffice.teacher.domain.exceptions.TeacherNotFoundException;
import com.tmoreno.mooc.shared.query.Query;

public final class FindTeacherQuery implements Query<FindTeacherQueryParams, FindTeacherQueryResponse> {

    private final TeacherRepository repository;

    public FindTeacherQuery(TeacherRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public FindTeacherQueryResponse execute(FindTeacherQueryParams params) {
        TeacherId id = new TeacherId(params.teacherId());

        Teacher teacher = repository.find(id).orElseThrow(() -> new TeacherNotFoundException(id));

        return FindTeacherQueryResponse.from(teacher);
    }
}
