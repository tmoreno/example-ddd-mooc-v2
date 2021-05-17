package com.tmoreno.mooc.backoffice.course.queries;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.shared.query.VoidQueryParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindCoursesQueryTest {

    @Mock
    private CourseRepository repository;

    private FindCoursesQuery query;

    @BeforeEach
    void setUp() {
        query = new FindCoursesQuery(repository);
    }

    @Test
    public void given_no_courses_when_find_then_return_empty_response() {
        FindCoursesQueryResponse response = query.execute(new VoidQueryParams());

        assertThat(response.getCourses(), is(empty()));
    }

    @Test
    public void given_there_are_three_courses_when_find_then_return_three_courses() {
        Course course1 = CourseMother.random();
        Course course2 = CourseMother.random();
        Course course3 = CourseMother.random();

        when(repository.findAll()).thenReturn(List.of(course1, course2, course3));

        FindCoursesQueryResponse response = query.execute(new VoidQueryParams());

        assertThat(response.getCourses().size(), is(3));
        assertCourse(response.getCourses().get(0), course1);
        assertCourse(response.getCourses().get(1), course2);
        assertCourse(response.getCourses().get(2), course3);
    }

    private void assertCourse(FindCourseQueryResponse response, Course course) {
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
}
