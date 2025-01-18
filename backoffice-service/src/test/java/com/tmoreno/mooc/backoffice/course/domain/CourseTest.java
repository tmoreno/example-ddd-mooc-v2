package com.tmoreno.mooc.backoffice.course.domain;

import com.tmoreno.mooc.backoffice.course.domain.events.CourseCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseDescriptionChangedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseDiscardedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseImageChangedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseLanguageChangedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CoursePriceChangedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CoursePublishedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSectionAddedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSectionClassAddedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSectionClassDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSectionClassDurationChangedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSectionClassTitleChangedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSectionDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSectionTitleChangedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseSummaryChangedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseTeacherAddedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseTeacherDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseTitleChangedDomainEvent;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.ChangeCourseAttributeException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseReviewNotFoundException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseSectionClassNotFoundException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseSectionNotFoundException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseStudentNotFoundException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseTeacherNotFoundException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.DiscardCourseException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.PublishCourseException;
import com.tmoreno.mooc.backoffice.common.mothers.CourseDescriptionMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseImageUrlMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseSummaryMother;
import com.tmoreno.mooc.backoffice.common.mothers.CourseTitleMother;
import com.tmoreno.mooc.backoffice.common.mothers.ReviewIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionClassIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionClassTitleMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionMother;
import com.tmoreno.mooc.backoffice.common.mothers.SectionTitleMother;
import com.tmoreno.mooc.backoffice.common.mothers.StudentIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.StudentMother;
import com.tmoreno.mooc.backoffice.common.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.common.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;
import com.tmoreno.mooc.shared.domain.Language;
import com.tmoreno.mooc.shared.domain.Price;
import com.tmoreno.mooc.shared.domain.TeacherId;
import com.tmoreno.mooc.shared.events.DomainEvent;
import com.tmoreno.mooc.shared.mothers.DurationInSecondsMother;
import com.tmoreno.mooc.shared.mothers.LanguageMother;
import com.tmoreno.mooc.shared.mothers.PriceMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseTest {

    @Test
    public void should_create_a_course_in_draft_state_and_record_an_event() {
        CourseId courseId = CourseIdMother.random();
        CourseTitle title = CourseTitleMother.random();

        Course course = Course.create(courseId, title);

        assertThat(course.getId(), is(courseId));
        assertThat(course.getTitle(), is(title));
        assertThat(course.getImageUrl(), is(Optional.empty()));
        assertThat(course.getSummary(), is(Optional.empty()));
        assertThat(course.getDescription(), is(Optional.empty()));
        assertThat(course.getState(), is(CourseState.DRAFT));
        assertThat(course.getLanguage(), is(Optional.empty()));
        assertThat(course.getPrice(), is(Optional.empty()));
        assertThat(course.getSections(), is(empty()));
        assertThat(course.getReviews(), is(emptyMap()));
        assertThat(course.getSections(), is(empty()));
        assertThat(course.getTeachers(), is(empty()));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseCreatedDomainEvent event = (CourseCreatedDomainEvent) domainEvents.get(0);

        assertThat(courseId.getValue(), is(event.getCourseId()));
        assertThat(title.getValue(), is(event.getTitle()));
    }

    @Test
    public void given_a_course_ready_to_publish_when_publish_then_course_is_published_and_an_event_is_recorded() {
        Course course = CourseMother.randomReadyToPublish();

        course.publish();

        assertThat(course.getState(), is(CourseState.PUBLISHED));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CoursePublishedDomainEvent event = (CoursePublishedDomainEvent) domainEvents.get(0);

        assertThat(course.getId().getValue(), is(event.getCourseId()));
    }

    @Test
    public void given_a_course_in_not_draft_state_when_publish_then_throws_an_exception() {
        assertThrows(PublishCourseException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.publish();
        });
    }

    @Test
    public void given_a_course_without_image_when_publish_then_throws_an_exception() {
        assertThrows(PublishCourseException.class, () -> {
            Course course = CourseMother.randomReadyToPublish();

            course.changeImageUrl(null);

            course.publish();
        });
    }

    @Test
    public void given_a_course_without_summary_when_publish_then_throws_an_exception() {
        assertThrows(PublishCourseException.class, () -> {
            Course course = CourseMother.randomReadyToPublish();

            course.changeSummary(null);

            course.publish();
        });
    }

    @Test
    public void given_a_course_without_description_when_publish_then_throws_an_exception() {
        assertThrows(PublishCourseException.class, () -> {
            Course course = CourseMother.randomReadyToPublish();

            course.changeDescription(null);

            course.publish();
        });
    }

    @Test
    public void given_a_course_without_language_when_publish_then_throws_an_exception() {
        assertThrows(PublishCourseException.class, () -> {
            Course course = CourseMother.randomReadyToPublish();

            course.changeLanguage(null);

            course.publish();
        });
    }

    @Test
    public void given_a_course_without_price_when_publish_then_throws_an_exception() {
        assertThrows(PublishCourseException.class, () -> {
            Course course = CourseMother.randomReadyToPublish();

            course.changePrice(null);

            course.publish();
        });
    }

    @Test
    public void given_a_course_without_sections_when_publish_then_throws_an_exception() {
        assertThrows(PublishCourseException.class, () -> {
            Course course = CourseMother.randomReadyToPublishWithoutSections();

            course.publish();
        });
    }

    @Test
    public void given_a_course_without_teachers_when_publish_then_throws_an_exception() {
        assertThrows(PublishCourseException.class, () -> {
            Course course = CourseMother.randomReadyToPublishWithoutTeachers();

            course.publish();
        });
    }

    @Test
    public void given_a_course_in_draft_state_when_discard_then_course_is_discarded_and_an_event_is_recorded() {
        Course course = CourseMother.randomInDraftState();

        course.discard();

        assertThat(course.getState(), is(CourseState.DISCARDED));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseDiscardedDomainEvent event = (CourseDiscardedDomainEvent) domainEvents.get(0);

        assertThat(course.getId().getValue(), is(event.getCourseId()));
    }

    @Test
    public void given_a_course_in_publish_state_when_discard_then_course_is_discarded_and_an_event_is_recorded() {
        Course course = CourseMother.randomInPublishState();

        course.discard();

        assertThat(course.getState(), is(CourseState.DISCARDED));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseDiscardedDomainEvent event = (CourseDiscardedDomainEvent) domainEvents.get(0);

        assertThat(course.getId().getValue(), is(event.getCourseId()));
    }

    @Test
    public void given_a_course_in_discarded_state_when_discard_then_throws_an_exception() {
        assertThrows(DiscardCourseException.class, () -> {
            Course course = CourseMother.randomInDiscardedState();

            course.discard();
        });
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_title_then_title_is_changed_and_an_event_is_recorded() {
        Course course = CourseMother.randomInDraftState();
        CourseTitle title = CourseTitleMother.random();

        course.changeTitle(title);

        assertThat(course.getTitle(), is(title));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseTitleChangedDomainEvent event = (CourseTitleChangedDomainEvent) domainEvents.get(0);

        assertThat(title.getValue(), is(event.getTitle()));
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_title_with_same_value_then_title_is_not_changed_and_an_event_is_not_recorded() {
        Course course = CourseMother.randomInDraftState();
        CourseTitle title = course.getTitle();

        course.changeTitle(title);

        assertThat(course.getTitle(), is(title));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.isEmpty(), is(true));
    }

    @Test
    public void given_a_course_in_not_draft_state_when_change_the_title_then_throws_an_exception() {
        Assertions.assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.changeTitle(CourseTitleMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_image_url_then_image_url_is_changed_and_an_event_is_recorded() {
        Course course = CourseMother.randomInDraftState();
        CourseImageUrl imageUrl = CourseImageUrlMother.random();

        course.changeImageUrl(imageUrl);

        assertThat(course.getImageUrl().get(), is(imageUrl));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseImageChangedDomainEvent event = (CourseImageChangedDomainEvent) domainEvents.get(0);

        assertThat(imageUrl.getValue(), is(event.getImageUrl()));
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_image_url_with_same_value_then_image_url_is_not_changed_and_event_is_not_recorded() {
        Course course = CourseMother.randomInDraftState();
        CourseImageUrl imageUrl = course.getImageUrl().get();

        course.changeImageUrl(imageUrl);

        assertThat(course.getImageUrl().get(), is(imageUrl));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.isEmpty(), is(true));
    }

    @Test
    public void given_a_course_in_not_draft_state_when_change_the_image_url_then_throws_an_exception() {
        Assertions.assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.changeImageUrl(CourseImageUrlMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_summary_then_summary_is_changed_and_an_event_is_recorded() {
        Course course = CourseMother.randomInDraftState();
        CourseSummary summary = CourseSummaryMother.random();

        course.changeSummary(summary);

        assertThat(course.getSummary().get(), is(summary));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseSummaryChangedDomainEvent event = (CourseSummaryChangedDomainEvent) domainEvents.get(0);

        assertThat(summary.getValue(), is(event.getSummary()));
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_summary_with_same_value_then_summary_is_not_changed_and_an_event_is_not_recorded() {
        Course course = CourseMother.randomInDraftState();
        CourseSummary summary = course.getSummary().get();

        course.changeSummary(summary);

        assertThat(course.getSummary().get(), is(summary));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.isEmpty(), is(true));
    }

    @Test
    public void given_a_course_in_not_draft_state_when_change_the_summary_then_throws_an_exception() {
        Assertions.assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.changeSummary(CourseSummaryMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_description_then_description_is_changed_and_an_event_is_recorded() {
        Course course = CourseMother.randomInDraftState();
        CourseDescription description = CourseDescriptionMother.random();

        course.changeDescription(description);

        assertThat(course.getDescription().get(), is(description));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseDescriptionChangedDomainEvent event = (CourseDescriptionChangedDomainEvent) domainEvents.get(0);

        assertThat(description.getValue(), is(event.getDescription()));
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_description_with_same_value_then_description_is_not_changed_and_an_event_is_not_recorded() {
        Course course = CourseMother.randomInDraftState();
        CourseDescription description = course.getDescription().get();

        course.changeDescription(description);

        assertThat(course.getDescription().get(), is(description));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.isEmpty(), is(true));
    }

    @Test
    public void given_a_course_in_not_draft_state_when_change_the_description_then_throws_an_exception() {
        Assertions.assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.changeDescription(CourseDescriptionMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_language_then_language_is_changed_and_an_event_is_recorded() {
        Course course = CourseMother.randomInDraftState();
        Language language = course.getLanguage().get() == Language.ENGLISH ? Language.SPANISH : Language.ENGLISH;

        course.changeLanguage(language);

        assertThat(course.getLanguage().get(), is(language));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseLanguageChangedDomainEvent event = (CourseLanguageChangedDomainEvent) domainEvents.get(0);

        assertThat(language.name(), is(event.getLanguage()));
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_language_with_same_value_then_language_is_not_changed_and_an_event_is_not_recorded() {
        Course course = CourseMother.randomInDraftState();
        Language language = course.getLanguage().get();

        course.changeLanguage(language);

        assertThat(course.getLanguage().get(), is(language));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.isEmpty(), is(true));
    }

    @Test
    public void given_a_course_in_not_draft_state_when_change_the_language_then_throws_an_exception() {
        Assertions.assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.changeLanguage(LanguageMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_price_then_price_is_changed_and_an_event_is_recorded() {
        Course course = CourseMother.randomInDraftState();
        Price price = PriceMother.random();

        course.changePrice(price);

        assertThat(course.getPrice().get(), is(price));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CoursePriceChangedDomainEvent event = (CoursePriceChangedDomainEvent) domainEvents.get(0);

        assertThat(price.getValue(), is(event.getPriceValue()));
        assertThat(price.getCurrency().getCurrencyCode(), is(event.getPriceCurrency()));
    }

    @Test
    public void given_a_course_in_draft_state_when_change_the_price_with_same_value_then_price_is_not_changed_and_an_event_not_is_recorded() {
        Course course = CourseMother.randomInDraftState();
        Price price = course.getPrice().get();

        course.changePrice(price);

        assertThat(course.getPrice().get(), is(price));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.isEmpty(), is(true));
    }

    @Test
    public void given_a_course_in_not_draft_state_when_change_the_price_then_throws_an_exception() {
        Assertions.assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.changePrice(PriceMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_when_add_a_section_then_section_is_added_and_an_event_is_recorded() {
        SectionId sectionId = SectionIdMother.random();
        SectionTitle sectionTitle = SectionTitleMother.random();

        Course course = CourseMother.randomInDraftState();
        course.addSection(sectionId, sectionTitle);

        assertThat(course.getSections().size(), is(1));
        assertThat(course.getSections().get(0).getId(), is(sectionId));
        assertThat(course.getSections().get(0).getTitle(), is(sectionTitle));
        assertThat(course.getSections().get(0).getClasses(), is(empty()));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseSectionAddedDomainEvent event = (CourseSectionAddedDomainEvent) domainEvents.get(0);

        assertThat(sectionId.getValue(), is(event.getSectionId()));
        assertThat(sectionTitle.getValue(), is(event.getTitle()));
    }

    @Test
    public void given_a_course_in_not_draft_state_when_add_a_section_then_throws_an_exception() {
        Assertions.assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.addSection(SectionIdMother.random(), SectionTitleMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_when_delete_a_section_then_section_is_deleted_and_an_event_is_recorded() {
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        course.deleteSection(section.getId());

        assertTrue(course.getSections().isEmpty());

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseSectionDeletedDomainEvent event = (CourseSectionDeletedDomainEvent) domainEvents.get(0);

        assertThat(section.getId().getValue(), is(event.getSectionId()));
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_when_delete_a_not_existing_section_then_throws_an_exception() {
        assertThrows(CourseSectionNotFoundException.class, () -> {
            Section section = SectionMother.random();
            Course course = CourseMother.randomInDraftStateWithSection(section);

            course.deleteSection(SectionIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_not_draft_state_when_delete_a_section_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.deleteSection(SectionIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_when_change_section_title_then_section_title_is_changed_and_an_event_is_recorded() {
        Section section = SectionMother.random();
        SectionTitle sectionTitle = SectionTitleMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        course.changeSectionTitle(section.getId(), sectionTitle);

        assertThat(getSection(course, section.getId()).getTitle(), is(sectionTitle));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseSectionTitleChangedDomainEvent event = (CourseSectionTitleChangedDomainEvent) domainEvents.get(0);

        assertThat(section.getId().getValue(), is(event.getSectionId()));
        assertThat(sectionTitle.getValue(), is(event.getTitle()));
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_when_change_section_title_with_same_value_then_section_title_is_not_changed_and_event_is_not_recorded() {
        Section section = SectionMother.random();
        SectionTitle sectionTitle = section.getTitle();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        course.changeSectionTitle(section.getId(), sectionTitle);

        assertThat(getSection(course, section.getId()).getTitle(), is(sectionTitle));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.isEmpty(), is(true));
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_when_change_section_title_for_a_not_existing_state_then_throws_an_exception() {
        assertThrows(CourseSectionNotFoundException.class, () -> {
            Section section = SectionMother.random();
            Course course = CourseMother.randomInDraftStateWithSection(section);

            course.changeSectionTitle(SectionIdMother.random(), SectionTitleMother.random());
        });
    }

    @Test
    public void given_a_course_in_not_draft_state_when_change_section_title_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.changeSectionTitle(SectionIdMother.random(), SectionTitleMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_when_add_a_class_then_class_is_added_and_an_event_is_recorded() {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        SectionClassTitle sectionClassTitle = SectionClassTitleMother.random();
        DurationInSeconds sectionClassDuration = DurationInSecondsMother.random();
        Section section = SectionMother.random();
        Course course = CourseMother.randomInDraftStateWithSection(section);

        course.addSectionClass(section.getId(), sectionClassId, sectionClassTitle, sectionClassDuration);

        assertThat(course.getSectionClasses(section.getId()).get(0).getId(), is(sectionClassId));
        assertThat(course.getSectionClasses(section.getId()).get(0).getTitle(), is(sectionClassTitle));
        assertThat(course.getSectionClasses(section.getId()).get(0).getDuration(), is(sectionClassDuration));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseSectionClassAddedDomainEvent event = (CourseSectionClassAddedDomainEvent) domainEvents.get(0);

        assertThat(section.getId().getValue(), is(event.getSectionId()));
        assertThat(sectionClassId.getValue(), is(event.getSectionClassId()));
        assertThat(sectionClassTitle.getValue(), is(event.getTitle()));
        assertThat(sectionClassDuration.value(), is(event.getDuration()));
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_when_add_a_class_to_not_existing_section_then_throws_an_exception() {
        assertThrows(CourseSectionNotFoundException.class, () -> {
            Section section = SectionMother.random();
            Course course = CourseMother.randomInDraftStateWithSection(section);

            course.addSectionClass(
                    SectionIdMother.random(),
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random(),
                    DurationInSecondsMother.random()
            );
        });
    }

    @Test
    public void given_a_course_in_not_draft_state_when_add_a_class_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.addSectionClass(
                    SectionIdMother.random(),
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random(),
                    DurationInSecondsMother.random()
            );
        });
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_and_classes_when_delete_an_existing_class_then_class_is_deleted_and_an_event_is_recorded() {
        SectionClassId sectionClassId = SectionClassIdMother.random();

        Section section = SectionMother.randomWithClass(
                sectionClassId,
                SectionClassTitleMother.random(),
                DurationInSecondsMother.random()
        );

        Course course = CourseMother.randomInDraftStateWithSection(section);

        course.deleteSectionClass(section.getId(), sectionClassId);

        assertThat(course.getSectionClasses(section.getId()), is(empty()));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseSectionClassDeletedDomainEvent event = (CourseSectionClassDeletedDomainEvent) domainEvents.get(0);

        assertThat(section.getId().getValue(), is(event.getSectionId()));
        assertThat(sectionClassId.getValue(), is(event.getSectionClassId()));
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_and_classes_when_delete_a_not_existing_class_then_throws_an_exception() {
        assertThrows(CourseSectionClassNotFoundException.class, () -> {
            Section section = SectionMother.randomWithClass(
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random(),
                    DurationInSecondsMother.random()
            );

            Course course = CourseMother.randomInDraftStateWithSection(section);

            course.deleteSectionClass(section.getId(), SectionClassIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_and_classes_when_delete_a_class_from_a_not_existing_section_then_throws_an_exception() {
        assertThrows(CourseSectionNotFoundException.class, () -> {
            Section section = SectionMother.randomWithClass(
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random(),
                    DurationInSecondsMother.random()
            );

            Course course = CourseMother.randomInDraftStateWithSection(section);

            course.deleteSectionClass(SectionIdMother.random(), SectionClassIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_not_draft_state_when_delete_a_class_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.deleteSectionClass(SectionIdMother.random(), SectionClassIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_and_classes_when_change_title_for_an_existing_class_then_class_title_is_changed_and_an_event_is_recorded() {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        SectionClassTitle sectionClassTitle = SectionClassTitleMother.random();

        Section section = SectionMother.randomWithClass(
                sectionClassId,
                SectionClassTitleMother.random(),
                DurationInSecondsMother.random()
        );

        Course course = CourseMother.randomInDraftStateWithSection(section);

        course.changeSectionClassTitle(section.getId(), sectionClassId, sectionClassTitle);

        assertThat(getSection(course, section.getId()).getClasses().get(0).getTitle(), is(sectionClassTitle));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseSectionClassTitleChangedDomainEvent event = (CourseSectionClassTitleChangedDomainEvent) domainEvents.get(0);

        assertThat(section.getId().getValue(), is(event.getSectionId()));
        assertThat(sectionClassId.getValue(), is(event.getSectionClassId()));
        assertThat(sectionClassTitle.getValue(), is(event.getTitle()));
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_and_classes_when_change_title_with_same_value_for_an_existing_class_then_class_title_is_not_changed_and_event_is_not_recorded() {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        SectionClassTitle sectionClassTitle = SectionClassTitleMother.random();

        Section section = SectionMother.randomWithClass(
                sectionClassId,
                sectionClassTitle,
                DurationInSecondsMother.random()
        );

        Course course = CourseMother.randomInDraftStateWithSection(section);

        course.changeSectionClassTitle(section.getId(), sectionClassId, sectionClassTitle);

        assertThat(getSection(course, section.getId()).getClasses().get(0).getTitle(), is(sectionClassTitle));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.isEmpty(), is(true));
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_and_classes_when_change_title_for_a_not_existing_class_then_throws_an_exception() {
        assertThrows(CourseSectionClassNotFoundException.class, () -> {
            Section section = SectionMother.randomWithClass(
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random(),
                    DurationInSecondsMother.random()
            );

            Course course = CourseMother.randomInDraftStateWithSection(section);

            course.changeSectionClassTitle(
                    section.getId(),
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random()
            );
        });
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_and_classes_when_change_a_class_title_for_a_not_existing_section_then_throws_an_exception() {
        assertThrows(CourseSectionNotFoundException.class, () -> {
            Section section = SectionMother.randomWithClass(
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random(),
                    DurationInSecondsMother.random()
            );

            Course course = CourseMother.randomInDraftStateWithSection(section);

            course.changeSectionClassTitle(
                    SectionIdMother.random(),
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random()
            );
        });
    }

    @Test
    public void given_a_course_in_not_draft_state_when_change_a_class_title_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.changeSectionClassTitle(
                    SectionIdMother.random(),
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random()
            );
        });
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_and_classes_when_change_duration_for_an_existing_class_then_class_duration_is_changed_and_an_event_is_recorded() {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        DurationInSeconds duration = DurationInSecondsMother.random();

        Section section = SectionMother.randomWithClass(
                sectionClassId,
                SectionClassTitleMother.random(),
                DurationInSecondsMother.random()
        );

        Course course = CourseMother.randomInDraftStateWithSection(section);

        course.changeSectionClassDuration(section.getId(), sectionClassId, duration);

        assertThat(getSection(course, section.getId()).getClasses().get(0).getDuration(), is(duration));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseSectionClassDurationChangedDomainEvent event = (CourseSectionClassDurationChangedDomainEvent) domainEvents.get(0);

        assertThat(section.getId().getValue(), is(event.getSectionId()));
        assertThat(sectionClassId.getValue(), is(event.getSectionClassId()));
        assertThat(duration.value(), is(event.getDuration()));
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_and_classes_when_change_duration_with_same_value_for_an_existing_class_then_class_duration_is_not_changed_and_event_is_not_recorded() {
        SectionClassId sectionClassId = SectionClassIdMother.random();
        DurationInSeconds duration = DurationInSecondsMother.random();

        Section section = SectionMother.randomWithClass(
                sectionClassId,
                SectionClassTitleMother.random(),
                duration
        );

        Course course = CourseMother.randomInDraftStateWithSection(section);

        course.changeSectionClassDuration(section.getId(), sectionClassId, duration);

        assertThat(getSection(course, section.getId()).getClasses().get(0).getDuration(), is(duration));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.isEmpty(), is(true));
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_and_classes_when_change_duration_for_a_not_existing_class_then_throws_an_exception() {
        assertThrows(CourseSectionClassNotFoundException.class, () -> {
            Section section = SectionMother.randomWithClass(
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random(),
                    DurationInSecondsMother.random()
            );

            Course course = CourseMother.randomInDraftStateWithSection(section);

            course.changeSectionClassDuration(
                    section.getId(),
                    SectionClassIdMother.random(),
                    DurationInSecondsMother.random()
            );
        });
    }

    @Test
    public void given_a_course_in_draft_state_with_sections_and_classes_when_change_a_class_duration_for_a_not_existing_section_then_throws_an_exception() {
        assertThrows(CourseSectionNotFoundException.class, () -> {
            Section section = SectionMother.randomWithClass(
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random(),
                    DurationInSecondsMother.random()
            );

            Course course = CourseMother.randomInDraftStateWithSection(section);

            course.changeSectionClassDuration(
                    SectionIdMother.random(),
                    SectionClassIdMother.random(),
                    DurationInSecondsMother.random()
            );
        });
    }

    @Test
    public void given_a_course_in_not_draft_state_when_change_a_class_duration_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.changeSectionClassDuration(
                    SectionIdMother.random(),
                    SectionClassIdMother.random(),
                    DurationInSecondsMother.random()
            );
        });
    }

    @Test
    public void given_a_course_in_published_state_when_add_a_review_then_review_is_added() {
        ReviewId reviewId = ReviewIdMother.random();
        StudentId studentId = StudentIdMother.random();

        Course course = CourseMother.randomInPublishState();

        course.addReview(reviewId, studentId);

        assertThat(course.getReviews().size(), is(1));
        assertThat(course.getReviews().get(studentId), is(reviewId));
    }

    @Test
    public void given_a_course_in_not_published_state_when_add_a_review_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotPublishState();

            course.addReview(ReviewIdMother.random(), StudentIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_published_state_with_reviews_when_delete_a_review_then_review_is_deleted() {
        StudentId studentId = StudentIdMother.random();
        ReviewId reviewId = ReviewIdMother.random();
        Course course = CourseMother.randomInPublishStateWithReview(studentId, reviewId);

        course.deleteReview(studentId, reviewId);

        assertThat(course.getReviews(), is(emptyMap()));
    }

    @Test
    public void given_a_course_in_published_state_with_reviews_when_delete_a_not_existing_review_then_throws_an_exception() {
        assertThrows(CourseReviewNotFoundException.class, () -> {
            StudentId studentId = StudentIdMother.random();
            ReviewId reviewId = ReviewIdMother.random();
            Course course = CourseMother.randomInPublishStateWithReview(studentId, reviewId);

            course.deleteReview(StudentIdMother.random(), ReviewIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_not_published_state_when_delete_a_review_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotPublishState();

            course.deleteReview(StudentIdMother.random(), ReviewIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_published_state_when_add_a_student_then_student_is_added() {
        StudentId studentId = StudentIdMother.random();

        Course course = CourseMother.randomInPublishState();

        course.addStudent(studentId);

        assertThat(course.getStudents().size(), is(1));
        assertThat(course.getStudents().contains(studentId), is(true));
    }

    @Test
    public void given_a_course_in_not_published_state_when_add_a_student_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotPublishState();

            course.addStudent(StudentIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_published_state_with_students_when_delete_a_student_then_student_is_deleted() {
        Student student = StudentMother.random();
        Course course = CourseMother.randomInPublishStateWithStudent(student);

        course.deleteStudent(student.getId());

        assertThat(course.getStudents(), is(empty()));
    }

    @Test
    public void given_a_course_in_published_state_with_students_when_delete_a_not_existing_student_then_throws_an_exception() {
        assertThrows(CourseStudentNotFoundException.class, () -> {
            Student student = StudentMother.random();
            Course course = CourseMother.randomInPublishStateWithStudent(student);

            course.deleteStudent(StudentIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_not_published_state_when_delete_a_student_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotPublishState();

            course.deleteStudent(StudentIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_when_add_a_teacher_then_teacher_is_added_and_an_event_is_recorded() {
        TeacherId teacherId = TeacherIdMother.random();

        Course course = CourseMother.randomInDraftState();

        course.addTeacher(teacherId);

        assertThat(course.getTeachers().size(), is(1));
        assertThat(course.getTeachers().contains(teacherId), is(true));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseTeacherAddedDomainEvent event = (CourseTeacherAddedDomainEvent) domainEvents.get(0);

        assertThat(teacherId.getValue(), is(event.getTeacherId()));
    }

    @Test
    public void given_a_course_in_not_draft_state_when_add_a_teacher_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.addTeacher(TeacherIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_draft_state_with_teachers_when_delete_an_existing_teacher_then_teacher_is_deleted_and_an_event_is_recorded() {
        Teacher teacher = TeacherMother.random();
        Course course = CourseMother.randomInDraftStateWithTeacher(teacher);

        course.deleteTeacher(teacher.getId());

        assertThat(course.getTeachers(), is(empty()));

        List<DomainEvent> domainEvents = course.pullEvents();
        assertThat(domainEvents.size(), is(1));

        CourseTeacherDeletedDomainEvent event = (CourseTeacherDeletedDomainEvent) domainEvents.get(0);

        assertThat(teacher.getId().getValue(), is(event.getTeacherId()));
    }

    @Test
    public void given_a_course_in_draft_state_with_teachers_when_delete_a_not_existing_teacher_then_throws_an_exception() {
        assertThrows(CourseTeacherNotFoundException.class, () -> {
            Teacher teacher = TeacherMother.random();
            Course course = CourseMother.randomInDraftStateWithTeacher(teacher);

            course.deleteTeacher(TeacherIdMother.random());
        });
    }

    @Test
    public void given_a_course_in_not_draft_state_when_delete_a_teacher_then_throws_an_exception() {
        assertThrows(ChangeCourseAttributeException.class, () -> {
            Course course = CourseMother.randomInNotDraftState();

            course.deleteTeacher(TeacherIdMother.random());
        });
    }

    private Section getSection(Course course, SectionId sectionId) {
        return course
                .getSections()
                .stream()
                .filter(section -> section.getId().equals(sectionId))
                .findFirst()
                .get();
    }
}
