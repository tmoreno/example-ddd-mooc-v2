package com.tmoreno.mooc.backoffice.students.queries;

import com.tmoreno.mooc.backoffice.students.domain.Student;
import com.tmoreno.mooc.backoffice.students.domain.StudentId;
import com.tmoreno.mooc.backoffice.students.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.students.domain.exceptions.StudentNotFoundException;
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
