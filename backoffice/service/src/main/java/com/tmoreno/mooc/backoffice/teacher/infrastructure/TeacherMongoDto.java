package com.tmoreno.mooc.backoffice.teacher.infrastructure;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.Email;
import com.tmoreno.mooc.shared.domain.Identifier;
import com.tmoreno.mooc.shared.domain.PersonName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.stream.Collectors;

@Document("teachers")
public final class TeacherMongoDto {

    @Id
    private String id;

    private String name;
    private String email;
    private Set<String> courses;

    public TeacherMongoDto() {

    }

    public TeacherMongoDto(String id, String name, String email, Set<String> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.courses = courses;
    }

    public static TeacherMongoDto fromTeacher(Teacher teacher) {
        return new TeacherMongoDto(
            teacher.getId().getValue(),
            teacher.getName().getValue(),
            teacher.getEmail().getValue(),
            teacher.getCourses().stream().map(Identifier::getValue).collect(Collectors.toSet())
        );
    }

    public static Teacher toTeacher(TeacherMongoDto teacherMongoDto) {
        return new Teacher(
            new TeacherId(teacherMongoDto.getId()),
            new PersonName(teacherMongoDto.getName()),
            new Email(teacherMongoDto.getEmail()),
            teacherMongoDto.getCourses().stream().map(CourseId::new).collect(Collectors.toSet())
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
