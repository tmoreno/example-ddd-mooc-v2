package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseState;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.shared.mothers.LanguageMother;
import com.tmoreno.mooc.shared.mothers.PriceMother;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class CourseMother {

    public static Course random() {
        StudentId studentId = StudentIdMother.random();

        return new Course(
            CourseIdMother.random(),
            CourseTitleMother.random(),
            CourseImageUrlMother.random(),
            CourseSummaryMother.random(),
            CourseDescriptionMother.random(),
            CourseStateMother.random(),
            LanguageMother.random(),
            PriceMother.random(),
            List.of(SectionMother.random()),
            Map.of(studentId, ReviewIdMother.random()),
            Set.of(studentId),
            Set.of(TeacherIdMother.random())
        );
    }

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
                Map.of(),
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
                Map.of(),
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

    public static Course randomInPublishStateWithReview(StudentId studentId, ReviewId reviewId) {
        Map<StudentId, ReviewId> reviews = new HashMap<>();
        reviews.put(studentId, reviewId);

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
                Map.of(),
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
                Map.of(),
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
                Map.of(),
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
                Map.of(),
                Set.of(),
                Set.of()
        );
    }
}
