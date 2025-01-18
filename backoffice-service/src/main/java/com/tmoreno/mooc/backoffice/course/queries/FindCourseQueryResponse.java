package com.tmoreno.mooc.backoffice.course.queries;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseDescription;
import com.tmoreno.mooc.backoffice.course.domain.CourseImageUrl;
import com.tmoreno.mooc.backoffice.course.domain.CourseSummary;
import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClass;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.Identifier;
import com.tmoreno.mooc.shared.domain.Price;
import com.tmoreno.mooc.shared.query.QueryResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class FindCourseQueryResponse implements QueryResponse {

    private final String id;
    private final String title;
    private final String imageUrl;
    private final String summary;
    private final String description;
    private final String state;
    private final String language;
    private final Double priceValue;
    private final String priceCurrency;
    private final List<SectionResponse> sections;
    private final List<StudentReviewResponse> reviews;
    private final Set<String> students;
    private final Set<String> teachers;

    public FindCourseQueryResponse(Course course) {
        this.id = course.getId().getValue();
        this.title = course.getTitle().value();
        this.imageUrl = course.getImageUrl().map(CourseImageUrl::value).orElse(null);
        this.summary = course.getSummary().map(CourseSummary::value).orElse(null);
        this.description = course.getDescription().map(CourseDescription::value).orElse(null);
        this.state = course.getState().name();
        this.language = course.getLanguage().map(Enum::name).orElse(null);
        this.priceValue = course.getPrice().map(Price::value).orElse(null);
        this.priceCurrency = course.getPrice().map(p->p.currency().getCurrencyCode()).orElse(null);
        this.sections = course.getSections().stream().map(SectionResponse::new).collect(Collectors.toList());
        this.students = course.getStudents().stream().map(Identifier::getValue).collect(Collectors.toSet());
        this.teachers = course.getTeachers().stream().map(Identifier::getValue).collect(Collectors.toSet());
        this.reviews = course.getReviews()
                .entrySet()
                .stream()
                .map(entry -> new StudentReviewResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
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

    public List<SectionResponse> getSections() {
        return sections;
    }

    public List<StudentReviewResponse> getReviews() {
        return reviews;
    }

    public Set<String> getStudents() {
        return students;
    }

    public Set<String> getTeachers() {
        return teachers;
    }

    public static class SectionResponse {
        private final String id;
        private final String title;
        private final List<SectionClassResponse> classes;

        public SectionResponse(Section section) {
            id = section.getId().getValue();
            title = section.getTitle().value();
            classes = section.getClasses().stream().map(SectionClassResponse::new).collect(Collectors.toList());
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public List<SectionClassResponse> getClasses() {
            return classes;
        }
    }

    public static class SectionClassResponse {
        private final String id;
        private final String title;
        private final int duration;

        public SectionClassResponse(SectionClass sectionClass) {
            id = sectionClass.getId().getValue();
            title = sectionClass.getTitle().value();
            duration = sectionClass.getDuration().value();
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public int getDuration() {
            return duration;
        }
    }

    public static class StudentReviewResponse {
        private final String studentId;
        private final String reviewId;

        public StudentReviewResponse(StudentId studentId, ReviewId reviewId) {
            this.studentId = studentId.getValue();
            this.reviewId = reviewId.getValue();
        }

        public String getStudentId() {
            return studentId;
        }

        public String getReviewId() {
            return reviewId;
        }
    }
}
