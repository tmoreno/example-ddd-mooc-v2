package com.tmoreno.mooc.backoffice.student.infrastructure;

import com.tmoreno.mooc.backoffice.student.domain.Student;
import com.tmoreno.mooc.shared.domain.Identifier;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "students")
public final class StudentJpaDto {

    @Id
    private String id;

    private String name;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "course_id")
    private Set<String> courses;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_reviews", joinColumns = @JoinColumn(name = "student_id"))
    @MapKeyColumn(name = "course_id")
    @Column(name = "review_id")
    private Map<String, String> reviews;

    public static StudentJpaDto fromStudent(Student student) {
        StudentJpaDto studentJpaDto = new StudentJpaDto();

        studentJpaDto.setId(student.getId().getValue());
        studentJpaDto.setName(student.getName().getValue());
        studentJpaDto.setEmail(student.getEmail().getValue());
        studentJpaDto.setCourses(student.getCourses().stream().map(Identifier::getValue).collect(Collectors.toSet()));

        Map<String, String> reviews = student.getReviews()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getValue(), entry -> entry.getValue().getValue()));
        studentJpaDto.setReviews(reviews);

        return studentJpaDto;
    }

    public static Student toStudent(StudentJpaDto studentJpaDto) {
        return Student.restore(
            studentJpaDto.getId(),
            studentJpaDto.getName(),
            studentJpaDto.getEmail(),
            studentJpaDto.getCourses(),
            studentJpaDto.getReviews()
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

    public Map<String, String> getReviews() {
        return reviews;
    }

    public void setReviews(Map<String, String> reviews) {
        this.reviews = reviews;
    }
}
