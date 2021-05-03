package com.tmoreno.mooc.backoffice.teachers.queries.find;

import com.tmoreno.mooc.backoffice.teachers.domain.TeacherRepository;
import com.tmoreno.mooc.shared.query.Query;
import com.tmoreno.mooc.shared.query.VoidQueryParams;

import java.util.List;
import java.util.stream.Collectors;

public final class FindTeachersQuery implements Query<VoidQueryParams, FindTeachersQueryResponse> {

    private final TeacherRepository repository;

    public FindTeachersQuery(TeacherRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public FindTeachersQueryResponse execute(VoidQueryParams params) {
        List<FindTeacherQueryResponse> teachers = repository.findAll()
                .stream()
                .map(FindTeacherQueryResponse::new)
                .collect(Collectors.toList());

        return new FindTeachersQueryResponse(teachers);
    }
}
