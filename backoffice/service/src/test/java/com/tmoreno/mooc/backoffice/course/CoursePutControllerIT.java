package com.tmoreno.mooc.backoffice.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseDescription;
import com.tmoreno.mooc.backoffice.course.domain.CourseImageUrl;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.CourseSummary;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.backoffice.mothers.CourseDescriptionMother;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseImageUrlMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.CourseSummaryMother;
import com.tmoreno.mooc.backoffice.mothers.CourseTitleMother;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import com.tmoreno.mooc.shared.domain.Language;
import com.tmoreno.mooc.shared.domain.Price;
import com.tmoreno.mooc.shared.mothers.LanguageMother;
import com.tmoreno.mooc.shared.mothers.PriceMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertErrorCode;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CoursePutControllerIT extends BaseControllerIT {

    @SpyBean
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        url += "/courses";
    }

    @Test
    public void given_existing_course_in_draft_state_when_send_put_request_then_receive_ok_response_and_course_is_changed_and_events_are_stored() {
        Course course = CourseMother.randomInDraftState();
        CourseTitle title = CourseTitleMother.random();
        CourseImageUrl imageUrl = CourseImageUrlMother.random();
        CourseSummary summary = CourseSummaryMother.random();
        CourseDescription description = CourseDescriptionMother.random();
        Language language = course.getLanguage().get() == Language.ENGLISH ? Language.SPANISH : Language.ENGLISH;
        Price price = PriceMother.random();

        courseRepository.save(course);

        ResponseEntity<String> response = put(
            course.getId().getValue(),
            Map.of(
                "title", title.getValue(),
                "imageUrl", imageUrl.getValue(),
                "summary", summary.getValue(),
                "description", description.getValue(),
                "language", language.name(),
                "priceValue", price.getValue(),
                "priceCurrency", price.getCurrency().getCurrencyCode()
            )
        );

        assertOk(response);

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getTitle(), is(title));
        assertThat(persistedCourse.getImageUrl(), is(Optional.of(imageUrl)));
        assertThat(persistedCourse.getSummary(), is(Optional.of(summary)));
        assertThat(persistedCourse.getDescription(), is(Optional.of(description)));
        assertThat(persistedCourse.getLanguage(), is(Optional.of(language)));
        assertThat(persistedCourse.getPrice(), is(Optional.of(price)));

        verify(domainEventRepository, times(6)).store(any());
    }

    @Test
    public void given_not_existing_course_when_send_put_request_then_receive_not_found_response() throws JsonProcessingException {
        ResponseEntity<String> response = put(CourseIdMother.random().getValue(), Map.of());

        assertNotFound(response);

        assertErrorCode(toJson(response.getBody()), "course-not-found");
    }

    @Test
    public void should_rollback_all_database_changes_when_an_exception_happen() {
        doThrow(RuntimeException.class).when(domainEventRepository).store(any());

        Price price = PriceMother.random();
        Course course = CourseMother.randomInDraftState();
        courseRepository.save(course);

        put(
            course.getId().getValue(),
            Map.of(
                "title", CourseTitleMother.random().getValue(),
                "imageUrl", CourseImageUrlMother.random().getValue(),
                "summary", CourseSummaryMother.random().getValue(),
                "description", CourseDescriptionMother.random().getValue(),
                "language", LanguageMother.random().name(),
                "priceValue", price.getValue(),
                "priceCurrency", price.getCurrency().getCurrencyCode()
            )
        );

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getTitle(), is(course.getTitle()));
        assertThat(persistedCourse.getImageUrl(), is(course.getImageUrl()));
        assertThat(persistedCourse.getSummary(), is(course.getSummary()));
        assertThat(persistedCourse.getDescription(), is(course.getDescription()));
        assertThat(persistedCourse.getLanguage(), is(course.getLanguage()));
        assertThat(persistedCourse.getPrice(), is(course.getPrice()));
    }
}
