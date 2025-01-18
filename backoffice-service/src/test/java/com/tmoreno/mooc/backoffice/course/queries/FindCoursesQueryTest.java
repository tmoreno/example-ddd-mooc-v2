package com.tmoreno.mooc.backoffice.course.queries;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClass;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.Identifier;
import com.tmoreno.mooc.shared.query.VoidQueryParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        assertThat(response.getDescription(), is(course.getDescription().get().value()));
        assertThat(response.getState(), is(course.getState().name()));
        assertThat(response.getLanguage(), is(course.getLanguage().get().name()));
        assertThat(response.getPriceValue(), is(course.getPrice().get().value()));
        assertThat(response.getPriceCurrency(), is(course.getPrice().get().currency().getCurrencyCode()));
        assertSections(response.getSections(), course.getSections());
        assertReviews(response.getReviews(), course.getReviews());
        assertThat(response.getStudents(), is(course.getStudents().stream().map(Identifier::getValue).collect(Collectors.toSet())));
        assertThat(response.getTeachers(), is(course.getTeachers().stream().map(Identifier::getValue).collect(Collectors.toSet())));
    }

    private void assertSections(List<FindCourseQueryResponse.SectionResponse> responseSections, List<Section> sections) {
        assertThat(responseSections.size(), is(sections.size()));

        for (FindCourseQueryResponse.SectionResponse responseSection : responseSections) {
            Section section = sections
                    .stream()
                    .filter(s -> responseSection.getId().equals(s.getId().getValue()))
                    .findFirst()
                    .orElseThrow();

            assertThat(responseSection.getTitle(), is(section.getTitle().getValue()));
            assertSectionClasses(responseSection.getClasses(), section.getClasses());
        }
    }

    private void assertSectionClasses(List<FindCourseQueryResponse.SectionClassResponse> responseClasses, List<SectionClass> classes) {
        assertThat(responseClasses.size(), is(classes.size()));

        for (FindCourseQueryResponse.SectionClassResponse responseClass : responseClasses) {
            SectionClass sectionClass = classes
                    .stream()
                    .filter(s -> responseClass.getId().equals(s.getId().getValue()))
                    .findFirst()
                    .orElseThrow();

            assertThat(responseClass.getTitle(), is(sectionClass.getTitle().value()));
            assertThat(responseClass.getDuration(), is(sectionClass.getDuration().value()));
        }
    }

    private void assertReviews(List<FindCourseQueryResponse.StudentReviewResponse> responseReviews, Map<StudentId, ReviewId> reviews) {
        assertThat(responseReviews.size(), is(reviews.size()));

        for (FindCourseQueryResponse.StudentReviewResponse responseReview : responseReviews) {
            ReviewId reviewId = reviews.get(new StudentId(responseReview.getStudentId()));

            assertThat(responseReview.getReviewId(), is(reviewId.getValue()));
        }
    }
}
