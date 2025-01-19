package com.tmoreno.mooc.backoffice.student.queries;

import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentRepository;
import com.tmoreno.mooc.shared.domain.Identifier;
import com.tmoreno.mooc.shared.query.VoidQueryParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindStudentsQueryTest {

    @Mock
    private StudentRepository repository;

    private FindStudentsQuery query;

    @BeforeEach
    void setUp() {
        query = new FindStudentsQuery(repository);
    }

    @Test
    public void given_no_students_when_find_then_return_empty_response() {
        FindStudentsQueryResponse response = query.execute(new VoidQueryParams());

        assertThat(response.getStudents(), is(empty()));
    }

    @Test
    public void given_there_are_three_students_when_find_then_return_three_students() {
        Student student1 = StudentMother.random();
        Student student2 = StudentMother.randomWithCourseAndReview(CourseIdMother.random(), ReviewIdMother.random());
        Student student3 = StudentMother.random();

        when(repository.findAll()).thenReturn(List.of(student1, student2, student3));

        FindStudentsQueryResponse response = query.execute(new VoidQueryParams());

        assertThat(response.getStudents().size(), is(3));
        assertStudent(response.getStudents().get(0), student1);
        assertStudent(response.getStudents().get(1), student2);
        assertStudent(response.getStudents().get(2), student3);
    }

    private void assertStudent(FindStudentQueryResponse response, Student student) {
        assertThat(response.getId(), is(student.getId().getValue()));
        assertThat(response.getName(), is(student.getName().value()));
        assertThat(response.getEmail(), is(student.getEmail().value()));
        assertThat(response.getCourses(), is(student.getCourses().stream().map(Identifier::getValue).collect(Collectors.toSet())));

        assertThat(response.getReviews().size(), is(student.getReviews().size()));
        for (FindStudentQueryResponse.CourseReviewResponse courseReviewResponse : response.getReviews()) {
            ReviewId reviewId = student.getReviews().get(new CourseId(courseReviewResponse.getCourseId()));

            assertThat(courseReviewResponse.getReviewId(), is(reviewId.getValue()));
        }
    }
}
