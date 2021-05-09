package com.tmoreno.mooc.backoffice.student.domain;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentCourseNotFoundException;
import com.tmoreno.mooc.backoffice.student.domain.exceptions.StudentReviewNotFoundException;
import com.tmoreno.mooc.shared.domain.AggregateRoot;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Student extends AggregateRoot<StudentId> {

    private PersonName name;
    private Email email;
    private final Set<CourseId> courses;
    private final Map<CourseId, ReviewId> reviews;

    public Student(StudentId id, PersonName name, Email email) {
        super(id);

        this.name = name;
        this.email = email;
        this.courses = new HashSet<>();
        this.reviews = new HashMap<>();
    }

    public Student(StudentId id, PersonName name, Email email, Set<CourseId> courses, Map<CourseId, ReviewId> reviews) {
        super(id);

        this.name = name;
        this.email = email;
        this.courses = courses;
        this.reviews = reviews;
    }

    public static Student create(StudentId id, PersonName name, Email email) {
        return new Student(id, name, email);
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
