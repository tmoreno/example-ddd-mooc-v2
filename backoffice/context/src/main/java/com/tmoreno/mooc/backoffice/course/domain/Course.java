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
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseSectionNotFoundException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseStudentNotFoundException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.CourseTeacherNotFoundException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.DiscardCourseException;
import com.tmoreno.mooc.backoffice.course.domain.exceptions.PublishCourseException;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.AggregateRoot;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;
import com.tmoreno.mooc.shared.domain.Language;
import com.tmoreno.mooc.shared.domain.Price;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class Course extends AggregateRoot<CourseId> {
    private CourseTitle title;
    private CourseImageUrl imageUrl;
    private CourseSummary summary;
    private CourseDescription description;
    private CourseState state;
    private Language language;
    private Price price;
    private final List<Section> sections;
    private final Map<StudentId, ReviewId> reviews;
    private final Set<StudentId> students;
    private final Set<TeacherId> teachers;

    public Course(
            CourseId id,
            CourseTitle title,
            CourseImageUrl imageUrl,
            CourseSummary summary,
            CourseDescription description,
            CourseState state,
            Language language,
            Price price
    ) {
        super(id);

        this.title = title;
        this.imageUrl = imageUrl;
        this.summary = summary;
        this.description = description;
        this.state = state;
        this.language = language;
        this.price = price;
        this.sections = new ArrayList<>();
        this.reviews = new HashMap<>();
        this.students = new HashSet<>();
        this.teachers = new HashSet<>();
    }

    public Course(
            CourseId id,
            CourseTitle title,
            CourseImageUrl imageUrl,
            CourseSummary summary,
            CourseDescription description,
            CourseState state,
            Language language,
            Price price,
            List<Section> sections,
            Map<StudentId, ReviewId> reviews,
            Set<StudentId> students,
            Set<TeacherId> teachers
    ) {
        super(id);

        this.title = title;
        this.imageUrl = imageUrl;
        this.summary = summary;
        this.description = description;
        this.state = state;
        this.language = language;
        this.price = price;
        this.sections = sections;
        this.reviews = reviews;
        this.students = students;
        this.teachers = teachers;
    }

    public static Course create(CourseId id, CourseTitle title) {
        Course course = new Course(
                id,
                title,
                null,
                null,
                null,
                CourseState.DRAFT,
                null,
                null
        );

        course.recordEvent(new CourseCreatedDomainEvent(id, title));

        return course;
    }

    public void publish() {
        if (state != CourseState.DRAFT) {
            throw new PublishCourseException("Course is not in DRAFT state");
        }

        if (title == null) {
            throw new PublishCourseException("Course title is not defined");
        }

        if (imageUrl == null) {
            throw new PublishCourseException("Course image url is not defined");
        }

        if (summary == null) {
            throw new PublishCourseException("Course summary is not defined");
        }

        if (description == null) {
            throw new PublishCourseException("Course description is not defined");
        }

        if (language == null) {
            throw new PublishCourseException("Course language is nos defined");
        }

        if (price == null) {
            throw new PublishCourseException("Course price is not defined");
        }

        if (sections.isEmpty()) {
            throw new PublishCourseException("Course sections is empty");
        }

        if (teachers.isEmpty()) {
            throw new PublishCourseException("Course teachers is empty");
        }

        state = CourseState.PUBLISHED;

        recordEvent(new CoursePublishedDomainEvent(id));
    }

    public void discard() {
        switch (state) {
            case DRAFT:
            case PUBLISHED:
                state = CourseState.DISCARDED;
                recordEvent(new CourseDiscardedDomainEvent(id));
                break;

            case DISCARDED:
                throw new DiscardCourseException("Course is already discarded");
        }
    }

    public CourseTitle getTitle() {
        return title;
    }

    public void changeTitle(CourseTitle title) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Change course title is not allowed because is not in DRAFT state");
        }

        if (title != null && !Objects.equals(title, this.title)) {
            this.title = title;

            recordEvent(new CourseTitleChangedDomainEvent(id, title));
        }
    }

    public Optional<CourseImageUrl> getImageUrl() {
        return Optional.ofNullable(imageUrl);
    }

    public void changeImageUrl(CourseImageUrl imageUrl) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Change course image url is not allowed because is not in DRAFT state");
        }

        if (!Objects.equals(imageUrl, this.imageUrl)) {
            this.imageUrl = imageUrl;

            recordEvent(new CourseImageChangedDomainEvent(id, imageUrl));
        }
    }

    public Optional<CourseSummary> getSummary() {
        return Optional.ofNullable(summary);
    }

    public void changeSummary(CourseSummary summary) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Change course summary is not allowed because is not in DRAFT state");
        }

        if (!Objects.equals(summary, this.summary)) {
            this.summary = summary;

            recordEvent(new CourseSummaryChangedDomainEvent(id, summary));
        }
    }

    public Optional<CourseDescription> getDescription() {
        return Optional.ofNullable(description);
    }

    public void changeDescription(CourseDescription description) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Change course description is not allowed because is not in DRAFT state");
        }

        if (!Objects.equals(description, this.description)) {
            this.description = description;

            recordEvent(new CourseDescriptionChangedDomainEvent(id, description));
        }
    }

    public CourseState getState() {
        return state;
    }

    public Optional<Language> getLanguage() {
        return Optional.ofNullable(language);
    }

    public void changeLanguage(Language language) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Change course language is not allowed because is not in DRAFT state");
        }

        if (!Objects.equals(language, this.language)) {
            this.language = language;

            recordEvent(new CourseLanguageChangedDomainEvent(id, language));
        }
    }

    public Optional<Price> getPrice() {
        return Optional.ofNullable(price);
    }

    public void changePrice(Price price) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Change course price is not allowed because is not in DRAFT state");
        }

        if (!Objects.equals(price, this.price)) {
            this.price = price;

            recordEvent(new CoursePriceChangedDomainEvent(id, price));
        }
    }

    public List<Section> getSections() {
        return List.copyOf(sections);
    }

    public void addSection(SectionId sectionId, SectionTitle title) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Add a section is not allowed because is not in DRAFT state");
        }

        Section section = new Section(sectionId, title);
        sections.add(section);

        recordEvent(new CourseSectionAddedDomainEvent(id, sectionId, title));
    }

    public void deleteSection(SectionId sectionId) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Delete a section is not allowed because is not in DRAFT state");
        }

        boolean removed = sections.removeIf(s -> s.getId().equals(sectionId));

        if (removed) {
            recordEvent(new CourseSectionDeletedDomainEvent(id, sectionId));
        }
        else {
            throw new CourseSectionNotFoundException(sectionId);
        }
    }

    public void changeSectionTitle(SectionId sectionId, SectionTitle title) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Change section title is not allowed because course is not in DRAFT state");
        }

        sections
            .stream()
            .filter(s -> s.getId().equals(sectionId))
            .findFirst()
            .ifPresentOrElse(
                section -> {
                    section.changeTitle(title);
                    recordEvent(new CourseSectionTitleChangedDomainEvent(id, sectionId, title));
                },
                ()-> {
                    throw new CourseSectionNotFoundException(sectionId);
                }
            );
    }

    public List<SectionClass> getSectionClasses(SectionId sectionId) {
        return List.copyOf(
            sections
                .stream()
                .filter(section -> section.getId().equals(sectionId))
                .findFirst()
                .orElseThrow(() -> new CourseSectionNotFoundException(sectionId))
                .getClasses()
        );
    }

    public void addSectionClass(SectionId sectionId, SectionClassId sectionClassId, SectionClassTitle title, DurationInSeconds duration) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Add section class is not allowed because course is not in DRAFT state");
        }

        sections
            .stream()
            .filter(s -> s.getId().equals(sectionId))
            .findFirst()
            .ifPresentOrElse(
                section -> {
                    section.addClass(sectionClassId, title, duration);
                    recordEvent(new CourseSectionClassAddedDomainEvent(id, sectionId, sectionClassId, title, duration));
                },
                ()-> {
                    throw new CourseSectionNotFoundException(sectionId);
                }
            );
    }

    public void deleteSectionClass(SectionId sectionId, SectionClassId sectionClassId) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Delete section class is not allowed because course is not in DRAFT state");
        }

        sections
            .stream()
            .filter(s -> s.getId().equals(sectionId))
            .findFirst()
            .ifPresentOrElse(
                section -> {
                    section.deleteClass(sectionClassId);
                    recordEvent(new CourseSectionClassDeletedDomainEvent(id, sectionId, sectionClassId));
                },
                ()-> {
                    throw new CourseSectionNotFoundException(sectionId);
                }
            );
    }

    public void changeSectionClassTitle(SectionId sectionId, SectionClassId sectionClassId, SectionClassTitle title) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Change section class title is not allowed because course is not in DRAFT state");
        }

        sections
            .stream()
            .filter(s -> s.getId().equals(sectionId))
            .findFirst()
            .ifPresentOrElse(
                section -> {
                    section.changeSectionClassTitle(sectionClassId, title);
                    recordEvent(new CourseSectionClassTitleChangedDomainEvent(id, sectionId, sectionClassId, title));
                },
                ()-> {
                    throw new CourseSectionNotFoundException(sectionId);
                }
            );
    }

    public void changeSectionClassDuration(SectionId sectionId, SectionClassId sectionClassId, DurationInSeconds duration) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Change section class duration is not allowed because course is not in DRAFT state");
        }

        sections
            .stream()
            .filter(s -> s.getId().equals(sectionId))
            .findFirst()
            .ifPresentOrElse(
                section -> {
                    section.changeSectionClassDuration(sectionClassId, duration);
                    recordEvent(new CourseSectionClassDurationChangedDomainEvent(id, sectionId, sectionClassId, duration));
                },
                ()-> {
                    throw new CourseSectionNotFoundException(sectionId);
                }
            );
    }

    public Map<StudentId, ReviewId> getReviews() {
        return Map.copyOf(reviews);
    }

    public void addReview(ReviewId reviewId, StudentId studentId) {
        if (state != CourseState.PUBLISHED) {
            throw new ChangeCourseAttributeException("Add a review is not allowed because is not in PUBLISH state");
        }

        reviews.put(studentId, reviewId);
    }

    public void deleteReview(StudentId studentId, ReviewId reviewId) {
        if (state != CourseState.PUBLISHED) {
            throw new ChangeCourseAttributeException("Delete a review is not allowed because is not in PUBLISH state");
        }

        boolean removed = reviews.remove(studentId, reviewId);

        if (!removed) {
            throw new CourseReviewNotFoundException(id, reviewId);
        }
    }

    public Set<StudentId> getStudents() {
        return Set.copyOf(students);
    }

    public void addStudent(StudentId studentId) {
        if (state != CourseState.PUBLISHED) {
            throw new ChangeCourseAttributeException("Add a student is not allowed because is not in PUBLISH state");
        }

        students.add(studentId);
    }

    public void deleteStudent(StudentId studentId) {
        if (state != CourseState.PUBLISHED) {
            throw new ChangeCourseAttributeException("Delete a student is not allowed because is not in PUBLISH state");
        }

        boolean removed = students.remove(studentId);

        if (!removed) {
            throw new CourseStudentNotFoundException(id, studentId);
        }
    }

    public Set<TeacherId> getTeachers() {
        return Set.copyOf(teachers);
    }

    public void addTeacher(TeacherId teacherId) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Add a teacher is not allowed because is not in DRAFT state");
        }

        teachers.add(teacherId);

        recordEvent(new CourseTeacherAddedDomainEvent(id, teacherId));
    }

    public void deleteTeacher(TeacherId teacherId) {
        if (state != CourseState.DRAFT) {
            throw new ChangeCourseAttributeException("Delete a teacher is not allowed because is not in DRAFT state");
        }

        boolean removed = teachers.remove(teacherId);

        if (removed) {
            recordEvent(new CourseTeacherDeletedDomainEvent(id, teacherId));
        }
        else {
            throw new CourseTeacherNotFoundException(id, teacherId);
        }
    }
}
