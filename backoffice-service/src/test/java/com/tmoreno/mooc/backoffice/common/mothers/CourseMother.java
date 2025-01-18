package com.tmoreno.mooc.backoffice.common.mothers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseState;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.shared.domain.Price;
import com.tmoreno.mooc.shared.mothers.DurationInSecondsMother;
import com.tmoreno.mooc.shared.mothers.LanguageMother;
import com.tmoreno.mooc.shared.mothers.PriceMother;
import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class CourseMother {

    public static Course random() {
        StudentId studentId = StudentIdMother.random();
        Price price = PriceMother.random();

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            CourseStateMother.random().name(),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            List.of(
                SectionMother.randomWithClass(
                    SectionClassIdMother.random(),
                    SectionClassTitleMother.random(),
                    DurationInSecondsMother.random()
                )
            ),
            Map.of(studentId.getValue(), ReviewIdMother.random().getValue()),
            Set.of(studentId.getValue()),
            Set.of(TeacherIdMother.random().getValue())
        );
    }

    public static Course randomInDraftState() {
        Price price = PriceMother.random();

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            CourseState.DRAFT.name(),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            Collections.emptyList(),
            Collections.emptyMap(),
            Collections.emptySet(),
            Collections.emptySet()
        );
    }

    public static Course randomInNotDraftState() {
        Price price = PriceMother.random();

        List<String> notDraftStates = Arrays.stream(CourseState.values())
            .filter(state -> state != CourseState.DRAFT)
            .map(Enum::name)
            .collect(Collectors.toList());

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            notDraftStates.get(RandomUtils.nextInt(0, notDraftStates.size())),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            Collections.emptyList(),
            Collections.emptyMap(),
            Collections.emptySet(),
            Collections.emptySet()
        );
    }

    public static Course randomInDraftStateWithSection(Section section) {
        Price price = PriceMother.random();

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            CourseState.DRAFT.name(),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            List.of(section),
            Collections.emptyMap(),
            Collections.emptySet(),
            Collections.emptySet()
        );
    }

    public static Course randomInDraftStateWithTeacher(Teacher teacher) {
        Price price = PriceMother.random();

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            CourseState.DRAFT.name(),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            Collections.emptyList(),
            Collections.emptyMap(),
            Collections.emptySet(),
            Set.of(teacher.getId().getValue())
        );
    }

    public static Course randomInPublishState() {
        Price price = PriceMother.random();

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            CourseState.PUBLISHED.name(),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            Collections.emptyList(),
            Collections.emptyMap(),
            Collections.emptySet(),
            Collections.emptySet()
        );
    }

    public static Course randomInNotPublishState() {
        Price price = PriceMother.random();

        List<String> notPublishedStates = Arrays.stream(CourseState.values())
            .filter(state -> state != CourseState.PUBLISHED)
            .map(Enum::name)
            .collect(Collectors.toList());

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            notPublishedStates.get(RandomUtils.nextInt(0, notPublishedStates.size())),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            Collections.emptyList(),
            Collections.emptyMap(),
            Collections.emptySet(),
            Collections.emptySet()
        );
    }

    public static Course randomInPublishStateWithReview(StudentId studentId, ReviewId reviewId) {
        Price price = PriceMother.random();

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            CourseState.PUBLISHED.name(),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            Collections.emptyList(),
            Map.of(studentId.getValue(), reviewId.getValue()),
            Collections.emptySet(),
            Collections.emptySet()
        );
    }

    public static Course randomInPublishStateWithStudent(Student student) {
        Price price = PriceMother.random();

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            CourseState.PUBLISHED.name(),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            Collections.emptyList(),
            Collections.emptyMap(),
            Set.of(student.getId().getValue()),
            Collections.emptySet()
        );
    }

    public static Course randomInDiscardedState() {
        Price price = PriceMother.random();

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            CourseState.DISCARDED.name(),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            Collections.emptyList(),
            Collections.emptyMap(),
            Collections.emptySet(),
            Collections.emptySet()
        );
    }

    public static Course randomReadyToPublish() {
        Price price = PriceMother.random();

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            CourseState.DRAFT.name(),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            List.of(SectionMother.random()),
            Collections.emptyMap(),
            Collections.emptySet(),
            Set.of(TeacherIdMother.random().getValue())
        );
    }

    public static Course randomReadyToPublishWithoutSections() {
        Price price = PriceMother.random();

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            CourseState.DRAFT.name(),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            Collections.emptyList(),
            Collections.emptyMap(),
            Collections.emptySet(),
            Set.of(TeacherIdMother.random().getValue())
        );
    }

    public static Course randomReadyToPublishWithoutTeachers() {
        Price price = PriceMother.random();

        return Course.restore(
            CourseIdMother.random().getValue(),
            CourseTitleMother.random().getValue(),
            CourseImageUrlMother.random().getValue(),
            CourseSummaryMother.random().getValue(),
            CourseDescriptionMother.random().getValue(),
            CourseState.DRAFT.name(),
            LanguageMother.random().name(),
            price.value(),
            price.currency().getCurrencyCode(),
            List.of(SectionMother.random()),
            Collections.emptyMap(),
            Collections.emptySet(),
            Collections.emptySet()
        );
    }
}
