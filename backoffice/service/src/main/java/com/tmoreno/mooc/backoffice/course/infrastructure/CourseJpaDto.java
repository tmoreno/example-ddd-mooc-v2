package com.tmoreno.mooc.backoffice.course.infrastructure;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseDescription;
import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.course.domain.CourseImageUrl;
import com.tmoreno.mooc.backoffice.course.domain.CourseState;
import com.tmoreno.mooc.backoffice.course.domain.CourseSummary;
import com.tmoreno.mooc.backoffice.course.domain.CourseTitle;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.Identifier;
import com.tmoreno.mooc.shared.domain.Language;
import com.tmoreno.mooc.shared.domain.Price;
import com.tmoreno.mooc.shared.domain.StringValueObject;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "courses")
public final class CourseJpaDto {

    @Id
    private String id;

    private String title;
    private String imageUrl;
    private String summary;
    private String description;
    private String state;
    private String language;
    private Double priceValue;
    private String priceCurrency;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<SectionJpaDto> sections;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "course_reviews", joinColumns = @JoinColumn(name = "course_id"))
    @MapKeyColumn(name = "student_id")
    @Column(name = "review_id")
    private Map<String, String> reviews;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "course_students", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "student_id")
    private Set<String> students;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "course_teachers", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "teacher_id")
    private Set<String> teachers;

    public static CourseJpaDto fromCourse(Course course) {
        CourseJpaDto courseJpaDto = new CourseJpaDto();

        courseJpaDto.setId(course.getId().getValue());
        courseJpaDto.setTitle(course.getTitle().getValue());
        courseJpaDto.setImageUrl(course.getImageUrl().map(StringValueObject::getValue).orElse(null));
        courseJpaDto.setSummary(course.getSummary().map(StringValueObject::getValue).orElse(null));
        courseJpaDto.setDescription(course.getDescription().map(StringValueObject::getValue).orElse(null));
        courseJpaDto.setState(course.getState().name());
        courseJpaDto.setLanguage(course.getLanguage().map(Enum::name).orElse(null));
        courseJpaDto.setPriceValue(course.getPrice().map(Price::getValue).orElse(null));
        courseJpaDto.setPriceCurrency(course.getPrice().map(p -> p.getCurrency().getCurrencyCode()).orElse(null));
        courseJpaDto.setSections(course.getSections().stream().map(SectionJpaDto::fromSection).collect(Collectors.toList()));
        courseJpaDto.setReviews(course.getReviews().entrySet().stream().collect(Collectors.toMap(e -> e.getKey().getValue(), e -> e.getValue().getValue())));
        courseJpaDto.setStudents(course.getStudents().stream().map(Identifier::getValue).collect(Collectors.toSet()));
        courseJpaDto.setTeachers(course.getTeachers().stream().map(Identifier::getValue).collect(Collectors.toSet()));

        return courseJpaDto;
    }

    public static Course toCourse(CourseJpaDto courseJpaDto) {
        return new Course(
            new CourseId(courseJpaDto.getId()),
            new CourseTitle(courseJpaDto.getTitle()),
            courseJpaDto.getImageUrl() == null ? null : new CourseImageUrl(courseJpaDto.getImageUrl()),
            courseJpaDto.getSummary() == null ? null : new CourseSummary(courseJpaDto.getSummary()),
            courseJpaDto.getDescription() == null ? null : new CourseDescription(courseJpaDto.getDescription()),
            CourseState.valueOf(courseJpaDto.getState()),
            courseJpaDto.getLanguage() == null ? null : Language.valueOf(courseJpaDto.getLanguage()),
            courseJpaDto.getPriceValue() == null ? null : new Price(courseJpaDto.getPriceValue(), Currency.getInstance(courseJpaDto.getPriceCurrency())),
            courseJpaDto.getSections().stream().map(SectionJpaDto::toSection).collect(Collectors.toList()),
            courseJpaDto.getReviews().entrySet().stream().collect(Collectors.toMap(e -> new StudentId(e.getKey()), e -> new ReviewId(e.getValue()))),
            courseJpaDto.getStudents().stream().map(StudentId::new).collect(Collectors.toSet()),
            courseJpaDto.getTeachers().stream().map(TeacherId::new).collect(Collectors.toSet())
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(Double priceValue) {
        this.priceValue = priceValue;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public List<SectionJpaDto> getSections() {
        return sections;
    }

    public void setSections(List<SectionJpaDto> sections) {
        this.sections = sections;
    }

    public Map<String, String> getReviews() {
        return reviews;
    }

    public void setReviews(Map<String, String> reviews) {
        this.reviews = reviews;
    }

    public Set<String> getStudents() {
        return students;
    }

    public void setStudents(Set<String> students) {
        this.students = students;
    }

    public Set<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<String> teachers) {
        this.teachers = teachers;
    }
}
