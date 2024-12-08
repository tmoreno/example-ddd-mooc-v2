package com.tmoreno.mooc.backoffice.student.queries;

import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentNotFoundException;
import com.tmoreno.mooc.shared.query.Query;

public final class FindStudentQuery implements Query<FindStudentQueryParams, FindStudentQueryResponse> {

    private final StudentRepository repository;

    public FindStudentQuery(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public FindStudentQueryResponse execute(FindStudentQueryParams params) {
        StudentId id = new StudentId(params.getStudentId());

        Student student = repository.find(id).orElseThrow(() -> new StudentNotFoundException(id));

        return new FindStudentQueryResponse(student);
    }
}
