package com.tmoreno.mooc.backoffice.review.infrastructure;

import com.tmoreno.mooc.backoffice.review.domain.Review;
import com.tmoreno.mooc.backoffice.review.domain.ReviewId;
import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ReviewJpaRepository implements ReviewRepository {

    private final ReviewDaoJpaRepository daoRepository;

    public ReviewJpaRepository(ReviewDaoJpaRepository daoRepository) {
        this.daoRepository = daoRepository;
    }

    @Override
    public void save(Review review) {
        ReviewJpaDto reviewJpaDto = ReviewJpaDto.fromReview(review);

        daoRepository.save(reviewJpaDto);
    }

    @Override
    public List<Review> findAll() {
        return daoRepository
                .findAll()
                .stream()
                .map(ReviewJpaDto::toReview)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Review> find(ReviewId id) {
        return daoRepository
                .findById(id.getValue())
                .map(ReviewJpaDto::toReview);
    }
}
