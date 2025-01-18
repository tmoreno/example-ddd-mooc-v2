package com.tmoreno.mooc.backoffice.review.queries;

import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.shared.query.QueryResponse;

public final class FindReviewQueryResponse implements QueryResponse {

    private final String id;
    private final String courseId;
    private final String studentId;
    private final double rating;
    private final String text;
    private final long createdOn;

    public FindReviewQueryResponse(Review review) {
        this.id = review.getId().getValue();
        this.courseId = review.getCourseId().getValue();
        this.studentId = review.getStudentId().getValue();
        this.rating = review.getRating().getValue();
        this.text = review.getText().getValue();
        this.createdOn = review.getCreatedOn().getValue();
    }

    public String getId() {
        return id;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public double getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }

    public long getCreatedOn() {
        return createdOn;
    }
}
