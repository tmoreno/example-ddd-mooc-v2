package com.tmoreno.mooc.backoffice.course.configuration;

import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.queries.FindCourseQuery;
import com.tmoreno.mooc.backoffice.course.queries.FindCoursesQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourseSpringBeansConfiguration {

    @Bean
    public FindCourseQuery findCourseQuery(CourseRepository repository) {
        return new FindCourseQuery(repository);
    }

    @Bean
    public FindCoursesQuery findCoursesQuery(CourseRepository repository) {
        return new FindCoursesQuery(repository);
    }
}
