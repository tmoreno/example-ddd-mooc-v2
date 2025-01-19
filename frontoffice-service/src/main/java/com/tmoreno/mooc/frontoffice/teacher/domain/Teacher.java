package com.tmoreno.mooc.frontoffice.teacher.domain;

import com.tmoreno.mooc.frontoffice.teacher.domain.exceptions.TeacherCourseNotFoundException;
import com.tmoreno.mooc.shared.domain.AggregateRoot;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.shared.domain.TeacherId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Teacher extends AggregateRoot<TeacherId> {

    private PersonName name;
    private Email email;
    private final Set<CourseId> courses;

    public Teacher(TeacherId id, PersonName name, Email email) {
        super(id);

        this.name = name;
        this.email = email;
        this.courses = new HashSet<>();
    }

    public Teacher(TeacherId id, PersonName name, Email email, Set<CourseId> courses) {
        super(id);

        this.name = name;
        this.email = email;
        this.courses = courses;
    }

    public static Teacher create(TeacherId id, PersonName name, Email email) {
        return new Teacher(id, name, email);
    }

    public PersonName getName() {
        return name;
    }

    public void changeName(PersonName name) {
        if (!Objects.equals(name, this.name)) {
            this.name = name;
        }
    }

    public Email getEmail() {
        return email;
    }

    public void changeEmail(Email email) {
        if (!Objects.equals(email, this.email)) {
            this.email = email;
        }
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
            throw new TeacherCourseNotFoundException(id, courseId);
        }
    }
}
