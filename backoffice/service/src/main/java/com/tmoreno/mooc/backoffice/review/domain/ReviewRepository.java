package com.tmoreno.mooc.backoffice.review.domain;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    void save(Review review);

    List<Review> findAll();

    Optional<Review> find(ReviewId id);
}
