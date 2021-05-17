package com.tmoreno.mooc.backoffice.course.queries;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindCourseQueryTest {

    @Mock
    private CourseRepository repository;

    private FindCourseQuery query;

    @BeforeEach
    public void setUp() {
        query = new FindCourseQuery(repository);
    }

    @Test
    public void given_a_existing_course_id_when_find_then_return_the_course() {
        CourseId courseId = CourseIdMother.random();
        Course course = CourseMother.random();

        when(repository.find(courseId)).thenReturn(Optional.of(course));

        FindCourseQueryParams params = new FindCourseQueryParams();
        params.setCourseId(courseId.getValue());

        FindCourseQueryResponse response = query.execute(params);

        assertThat(response.getId(), is(course.getId().getValue()));
        assertThat(response.getTitle(), is(course.getTitle().getValue()));
        assertThat(response.getImageUrl(), is(course.getImageUrl().get().getValue()));
        assertThat(response.getSummary(), is(course.getSummary().get().getValue()));
        assertThat(response.getDescription(), is(course.getDescription().get().getValue()));
        assertThat(response.getState(), is(course.getState().name()));
        assertThat(response.getLanguage(), is(course.getLanguage().get().name()));
        assertThat(response.getPriceValue(), is(course.getPrice().get().getValue()));
        assertThat(response.getPriceCurrency(), is(course.getPrice().get().getCurrency().getCurrencyCode()));
        assertThat(response.getSections(), is(course.getSections()));
        assertThat(response.getReviews(), is(course.getReviews()));
        assertThat(response.getStudents(), is(course.getStudents()));
        assertThat(response.getTeachers(), is(course.getTeachers()));
    }

    @Test
    public void given_a_not_existing_course_id_when_find_then_throws_an_exception() {
        Assertions.assertThrows(CourseNotFoundException.class, () -> {
            CourseId courseId = CourseIdMother.random();

            when(repository.find(courseId)).thenReturn(Optional.empty());

            FindCourseQueryParams params = new FindCourseQueryParams();
            params.setCourseId(courseId.getValue());

            query.execute(params);
        });
    }
}
