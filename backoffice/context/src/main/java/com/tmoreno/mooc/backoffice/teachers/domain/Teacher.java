package com.tmoreno.mooc.backoffice.teachers.domain;

import com.tmoreno.mooc.backoffice.courses.domain.CourseId;
import com.tmoreno.mooc.shared.domain.AggregateRoot;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;
import com.tmoreno.mooc.backoffice.teachers.domain.events.TeacherCourseAddedDomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.events.TeacherCourseDeletedDomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.events.TeacherCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.events.TeacherEmailChangedDomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.events.TeacherNameChangedDomainEvent;
import com.tmoreno.mooc.backoffice.teachers.domain.exceptions.TeacherCourseNotFoundException;

import java.util.HashSet;
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
        Teacher teacher = new Teacher(id, name, email);

        teacher.recordEvent(new TeacherCreatedDomainEvent(id, name, email));

        return teacher;
    }

    public PersonName getName() {
        return name;
    }

    public void changeName(PersonName name) {
        this.name = name;

        recordEvent(new TeacherNameChangedDomainEvent(id, name));
    }

    public Email getEmail() {
        return email;
    }

    public void changeEmail(Email email) {
        this.email = email;

        recordEvent(new TeacherEmailChangedDomainEvent(id, email));
    }

    public Set<CourseId> getCourses() {
        return Set.copyOf(courses);
    }

    public void addCourse(CourseId courseId) {
        courses.add(courseId);

        recordEvent(new TeacherCourseAddedDomainEvent(id, courseId));
    }

    public void deleteCourse(CourseId courseId) {
        boolean removed = courses.remove(courseId);

        if (removed) {
            recordEvent(new TeacherCourseDeletedDomainEvent(id, courseId));
        }
        else {
            throw new TeacherCourseNotFoundException(id, courseId);
        }
    }
}
