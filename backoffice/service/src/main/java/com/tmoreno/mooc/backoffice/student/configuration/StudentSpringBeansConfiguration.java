package com.tmoreno.mooc.backoffice.student.configuration;

import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.backoffice.student.queries.FindStudentQuery;
import com.tmoreno.mooc.backoffice.student.queries.FindStudentsQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentSpringBeansConfiguration {

    @Bean
    public FindStudentQuery findStudentQuery(StudentRepository repository) {
        return new FindStudentQuery(repository);
    }

    @Bean
    public FindStudentsQuery findStudentsQuery(StudentRepository repository) {
        return new FindStudentsQuery(repository);
    }
}
