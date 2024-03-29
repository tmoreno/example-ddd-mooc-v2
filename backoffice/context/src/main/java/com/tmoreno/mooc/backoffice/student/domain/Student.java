package com.tmoreno.mooc.backoffice.student.domain;

import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentCourseNotFoundException;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentReviewNotFoundException;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.Entity;
import com.tmoreno.mooc.shared.domain.PersonName;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class Student extends Entity<StudentId> {

    private PersonName name;
    private Email email;
    private final Set<CourseId> courses;
    private final Map<CourseId, ReviewId> reviews;

    private Student(StudentId id, PersonName name, Email email, Set<CourseId> courses, Map<CourseId, ReviewId> reviews) {
        super(id);

        this.name = name;
        this.email = email;
        this.courses = courses;
        this.reviews = reviews;
    }

    public static Student restore(String id, String name, String email, Set<String> courses, Map<String, String> reviews) {
        return new Student(
            new StudentId(id),
            new PersonName(name),
            new Email(email),
            courses.stream().map(CourseId::new).collect(Collectors.toSet()),
            reviews.entrySet().stream().collect(Collectors.toMap(
                e -> new CourseId(e.getKey()),
                e -> new ReviewId(e.getValue())
            ))
        );
    }

    public PersonName getName() {
        return name;
    }

    public void changeName(PersonName name) {
        this.name = name;
    }

    public Email getEmail() {
        return email;
    }

    public void changeEmail(Email email) {
        this.email = email;
    }

    public Set<CourseId> getCourses() {
        return Set.copyOf(courses);
    }

    public void addCourse(CourseId courseId) {
        courses.add(courseId);
    }

    public void deleteCourse(CourseId courseId) {
        boolean removed = courses.remove(courseId);

        if (!removed) {
            throw new StudentCourseNotFoundException(id, courseId);
        }
    }

    public Map<CourseId, ReviewId> getReviews() {
        return Map.copyOf(reviews);
    }

    public void addReview(CourseId courseId, ReviewId reviewId) {
        reviews.put(courseId, reviewId);
    }

    public void deleteReview(CourseId courseId, ReviewId reviewId) {
        boolean removed = reviews.remove(courseId, reviewId);

        if (!removed) {
            throw new StudentReviewNotFoundException(id, reviewId);
        }
    }
}
