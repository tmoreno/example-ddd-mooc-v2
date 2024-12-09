package com.tmoreno.mooc.backoffice.teacher.infrastructure.repository;

import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.shared.domain.Identifier;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "teachers")
public final class TeacherJpaDto {

    @Id
    private String id;

    private String name;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "teacher_courses", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "course_id")
    private Set<String> courses;

    public TeacherJpaDto() {

    }

    public TeacherJpaDto(String id, String name, String email, Set<String> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.courses = courses;
    }

    public static TeacherJpaDto fromTeacher(Teacher teacher) {
        return new TeacherJpaDto(
            teacher.getId().getValue(),
            teacher.getName().getValue(),
            teacher.getEmail().getValue(),
            teacher.getCourses().stream().map(Identifier::getValue).collect(Collectors.toSet())
        );
    }

    public static Teacher toTeacher(TeacherJpaDto teacherJpaDto) {
        return Teacher.restore(
            teacherJpaDto.getId(),
            teacherJpaDto.getName(),
            teacherJpaDto.getEmail(),
            teacherJpaDto.getCourses()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getCourses() {
        return courses;
    }

    public void setCourses(Set<String> courses) {
        this.courses = courses;
    }
}
