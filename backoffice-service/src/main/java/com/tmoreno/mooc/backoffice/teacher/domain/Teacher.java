package com.tmoreno.mooc.backoffice.teacher.domain;

import com.tmoreno.mooc.backoffice.teacher.domain.events.TeacherCreatedDomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.events.TeacherEmailChangedDomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.events.TeacherNameChangedDomainEvent;
import com.tmoreno.mooc.backoffice.teacher.domain.exceptions.TeacherCourseNotFoundException;
import com.tmoreno.mooc.shared.domain.AggregateRoot;
import com.tmoreno.mooc.shared.domain.CourseId;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.PersonName;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class Teacher extends AggregateRoot<TeacherId> {

    private PersonName name;
    private Email email;
    private final Set<CourseId> courses;

    private Teacher(TeacherId id, PersonName name, Email email, Set<CourseId> courses) {
        super(id);

        this.name = name;
        this.email = email;
        this.courses = courses;
    }

    public static Teacher create(TeacherId id, PersonName name, Email email) {
        Teacher teacher = new Teacher(id, name, email, new HashSet<>());

        teacher.recordEvent(new TeacherCreatedDomainEvent(id, name, email));

        return teacher;
    }

    public static Teacher restore(String id, String name, String email, Set<String> courses) {
        return new Teacher(
            new TeacherId(id),
            new PersonName(name),
            new Email(email),
            courses.stream().map(CourseId::new).collect(Collectors.toSet())
        );
    }

    public PersonName getName() {
        return name;
    }

    public void changeName(PersonName name) {
        if (Objects.equals(name, this.name)) {
            return;
        }

        this.name = name;

        recordEvent(new TeacherNameChangedDomainEvent(id, name));
    }

    public Email getEmail() {
        return email;
    }

    public void changeEmail(Email email) {
        if (Objects.equals(email, this.email)) {
            return;
        }
        
        this.email = email;

        recordEvent(new TeacherEmailChangedDomainEvent(id, email));
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
