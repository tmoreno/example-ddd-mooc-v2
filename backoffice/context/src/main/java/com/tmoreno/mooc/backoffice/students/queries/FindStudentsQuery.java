package com.tmoreno.mooc.backoffice.students.queries;

import com.tmoreno.mooc.backoffice.students.domain.StudentRepository;
import com.tmoreno.mooc.shared.query.Query;
import com.tmoreno.mooc.shared.query.VoidQueryParams;

import java.util.List;
import java.util.stream.Collectors;

public final class FindStudentsQuery implements Query<VoidQueryParams, FindStudentsQueryResponse> {

    private final StudentRepository repository;

    public FindStudentsQuery(StudentRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public FindStudentsQueryResponse execute(VoidQueryParams params) {
        List<FindStudentQueryResponse> students = repository.findAll()
                .stream()
                .map(FindStudentQueryResponse::new)
                .collect(Collectors.toList());

        return new FindStudentsQueryResponse(students);
    }
}
