package com.tmoreno.mooc.backoffice.course.queries;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherId;
import com.tmoreno.mooc.shared.domain.Price;
import com.tmoreno.mooc.shared.domain.StringValueObject;
import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FindCourseQueryResponse extends QueryResponse {

    private final String id;
    private final String title;
    private final String imageUrl;
    private final String summary;
    private final String description;
    private final String state;
    private final String language;
    private final Double priceValue;
    private final String priceCurrency;
    private final List<Section> sections;
    private final Map<StudentId, ReviewId> reviews;
    private final Set<StudentId> students;
    private final Set<TeacherId> teachers;

    public FindCourseQueryResponse(Course course) {
        this.id = course.getId().getValue();
        this.title = course.getTitle().getValue();
        this.imageUrl = course.getImageUrl().map(StringValueObject::getValue).orElse(null);
        this.summary = course.getSummary().map(StringValueObject::getValue).orElse(null);
        this.description = course.getDescription().map(StringValueObject::getValue).orElse(null);
        this.state = course.getState().name();
        this.language = course.getLanguage().map(Enum::name).orElse(null);
        this.priceValue = course.getPrice().map(Price::getValue).orElse(null);
        this.priceCurrency = course.getPrice().map(p->p.getCurrency().getCurrencyCode()).orElse(null);
        this.sections = course.getSections();
        this.reviews = course.getReviews();
        this.students = course.getStudents();
        this.teachers = course.getTeachers();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public String getLanguage() {
        return language;
    }

    public Double getPriceValue() {
        return priceValue;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public List<Section> getSections() {
        return sections;
    }

    public Map<StudentId, ReviewId> getReviews() {
        return reviews;
    }

    public Set<StudentId> getStudents() {
        return students;
    }

    public Set<TeacherId> getTeachers() {
        return teachers;
    }
}
