package com.tmoreno.mooc.backoffice.course.queries;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClass;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseNotFoundException;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.Identifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        assertSections(response.getSections(), course.getSections());
        assertReviews(response.getReviews(), course.getReviews());
        assertThat(response.getStudents(), is(course.getStudents().stream().map(Identifier::getValue).collect(Collectors.toSet())));
        assertThat(response.getTeachers(), is(course.getTeachers().stream().map(Identifier::getValue).collect(Collectors.toSet())));
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

            assertThat(responseClass.getTitle(), is(sectionClass.getTitle().getValue()));
            assertThat(responseClass.getDuration(), is(sectionClass.getDuration().getValue()));
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
