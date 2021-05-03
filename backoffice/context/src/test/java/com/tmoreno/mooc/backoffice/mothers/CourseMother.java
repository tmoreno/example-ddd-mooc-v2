package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.courses.domain.Course;
import com.tmoreno.mooc.backoffice.courses.domain.CourseState;
import com.tmoreno.mooc.backoffice.courses.domain.Review;
import com.tmoreno.mooc.backoffice.courses.domain.Section;
import com.tmoreno.mooc.shared.mothers.LanguageMother;
import com.tmoreno.mooc.shared.mothers.PriceMother;
import com.tmoreno.mooc.backoffice.students.domain.Student;
import com.tmoreno.mooc.backoffice.students.domain.StudentId;
import com.tmoreno.mooc.backoffice.teachers.domain.Teacher;
import com.tmoreno.mooc.backoffice.teachers.domain.TeacherId;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class CourseMother {
    public static Course randomInDraftState() {
        return new Course(
            CourseIdMother.random(),
            CourseTitleMother.random(),
            CourseImageUrlMother.random(),
            CourseSummaryMother.random(),
            CourseDescriptionMother.random(),
            CourseState.DRAFT,
            LanguageMother.random(),
            PriceMother.random()
        );
    }

    public static Course randomInNotDraftState() {
        List<CourseState> notDraftStates = Arrays.stream(CourseState.values())
                .filter(state -> state != CourseState.DRAFT)
                .collect(Collectors.toList());

        return new Course(
                CourseIdMother.random(),
                CourseTitleMother.random(),
                CourseImageUrlMother.random(),
                CourseSummaryMother.random(),
                CourseDescriptionMother.random(),
                notDraftStates.get(RandomUtils.nextInt(0, notDraftStates.size())),
                LanguageMother.random(),
                PriceMother.random()
        );
    }

    public static Course randomInDraftStateWithSection(Section section) {
        List<Section> sections = new ArrayList<>();
        sections.add(section);

        return new Course(
                CourseIdMother.random(),
                CourseTitleMother.random(),
                CourseImageUrlMother.random(),
                CourseSummaryMother.random(),
                CourseDescriptionMother.random(),
                CourseState.DRAFT,
                LanguageMother.random(),
                PriceMother.random(),
                sections,
                List.of(),
                Set.of(),
                Set.of()
        );
    }

    public static Course randomInDraftStateWithTeacher(Teacher teacher) {
        Set<TeacherId> teachers = new HashSet<>();
        teachers.add(teacher.getId());

        return new Course(
                CourseIdMother.random(),
                CourseTitleMother.random(),
                CourseImageUrlMother.random(),
                CourseSummaryMother.random(),
                CourseDescriptionMother.random(),
                CourseState.DRAFT,
                LanguageMother.random(),
                PriceMother.random(),
                List.of(),
                List.of(),
                Set.of(),
                teachers
        );
    }

    public static Course randomInPublishState() {
        return new Course(
                CourseIdMother.random(),
                CourseTitleMother.random(),
                CourseImageUrlMother.random(),
                CourseSummaryMother.random(),
                CourseDescriptionMother.random(),
                CourseState.PUBLISHED,
                LanguageMother.random(),
                PriceMother.random()
        );
    }

    public static Course randomInNotPublishState() {
        List<CourseState> notPublishedStates = Arrays.stream(CourseState.values())
                .filter(state -> state != CourseState.PUBLISHED)
                .collect(Collectors.toList());

        return new Course(
                CourseIdMother.random(),
                CourseTitleMother.random(),
                CourseImageUrlMother.random(),
                CourseSummaryMother.random(),
                CourseDescriptionMother.random(),
                notPublishedStates.get(RandomUtils.nextInt(0, notPublishedStates.size())),
                LanguageMother.random(),
                PriceMother.random()
        );
    }

    public static Course randomInPublishStateWithReview(Review review) {
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        return new Course(
                CourseIdMother.random(),
                CourseTitleMother.random(),
                CourseImageUrlMother.random(),
                CourseSummaryMother.random(),
                CourseDescriptionMother.random(),
                CourseState.PUBLISHED,
                LanguageMother.random(),
                PriceMother.random(),
                List.of(),
                reviews,
                Set.of(),
                Set.of()
        );
    }

    public static Course randomInPublishStateWithStudent(Student student) {
        Set<StudentId> students = new HashSet<>();
        students.add(student.getId());

        return new Course(
                CourseIdMother.random(),
                CourseTitleMother.random(),
                CourseImageUrlMother.random(),
                CourseSummaryMother.random(),
                CourseDescriptionMother.random(),
                CourseState.PUBLISHED,
                LanguageMother.random(),
                PriceMother.random(),
                List.of(),
                List.of(),
                students,
                Set.of()
        );
    }

    public static Course randomInDiscardedState() {
        return new Course(
                CourseIdMother.random(),
                CourseTitleMother.random(),
                CourseImageUrlMother.random(),
                CourseSummaryMother.random(),
                CourseDescriptionMother.random(),
                CourseState.DISCARDED,
                LanguageMother.random(),
                PriceMother.random()
        );
    }

    public static Course randomReadyToPublish() {
        return new Course(
                CourseIdMother.random(),
                CourseTitleMother.random(),
                CourseImageUrlMother.random(),
                CourseSummaryMother.random(),
                CourseDescriptionMother.random(),
                CourseState.DRAFT,
                LanguageMother.random(),
                PriceMother.random(),
                List.of(SectionMother.random()),
                List.of(),
                Set.of(),
                Set.of(TeacherIdMother.random())
        );
    }

    public static Course randomReadyToPublishWithoutSections() {
        return new Course(
                CourseIdMother.random(),
                CourseTitleMother.random(),
                CourseImageUrlMother.random(),
                CourseSummaryMother.random(),
                CourseDescriptionMother.random(),
                CourseState.DRAFT,
                LanguageMother.random(),
                PriceMother.random(),
                List.of(),
                List.of(),
                Set.of(),
                Set.of(TeacherIdMother.random())
        );
    }

    public static Course randomReadyToPublishWithoutTeachers() {
        return new Course(
                CourseIdMother.random(),
                CourseTitleMother.random(),
                CourseImageUrlMother.random(),
                CourseSummaryMother.random(),
                CourseDescriptionMother.random(),
                CourseState.DRAFT,
                LanguageMother.random(),
                PriceMother.random(),
                List.of(SectionMother.random()),
                List.of(),
                Set.of(),
                Set.of()
        );
    }
}
