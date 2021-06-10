package com.tmoreno.mooc.backoffice.review.infrastructure;

import com.tmoreno.mooc.backoffice.course.domain.CourseId;
import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRating;
import com.tmoreno.mooc.backoffice.review.domain.ReviewText;
import com.tmoreno.mooc.backoffice.student.domain.StudentId;
import com.tmoreno.mooc.shared.domain.CreatedOn;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity(name = "reviews")
public final class ReviewJpaDto {

    @Id
    private String id;

    private String courseId;
    private String studentId;
    private String rating;
    private String text;
    private Instant createdOn;

    public static ReviewJpaDto fromReview(Review review) {
        ReviewJpaDto reviewJpaDto = new ReviewJpaDto();

        reviewJpaDto.setId(review.getId().getValue());
        reviewJpaDto.setCourseId(review.getCourseId().getValue());
        reviewJpaDto.setStudentId(review.getStudentId().getValue());
        reviewJpaDto.setRating(review.getRating().name());
        reviewJpaDto.setText(review.getText().getValue());
        reviewJpaDto.setCreatedOn(Instant.ofEpochMilli(review.getCreatedOn().getValue()));

        return reviewJpaDto;
    }

    public static Review toReview(ReviewJpaDto reviewJpaDto) {
        return new Review(
            new ReviewId(reviewJpaDto.getId()),
            new CourseId(reviewJpaDto.getCourseId()),
            new StudentId(reviewJpaDto.getStudentId()),
            ReviewRating.valueOf(reviewJpaDto.getRating()),
            new ReviewText(reviewJpaDto.getText()),
            new CreatedOn(reviewJpaDto.getCreatedOn().toEpochMilli())
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
}
