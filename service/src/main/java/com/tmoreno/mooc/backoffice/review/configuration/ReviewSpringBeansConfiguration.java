package com.tmoreno.mooc.backoffice.review.configuration;

import com.tmoreno.mooc.backoffice.review.domain.ReviewRepository;
import com.tmoreno.mooc.backoffice.review.queries.FindReviewQuery;
import com.tmoreno.mooc.backoffice.review.queries.FindReviewsQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewSpringBeansConfiguration {

    @Bean
    public FindReviewQuery findReviewQuery(ReviewRepository repository) {
        return new FindReviewQuery(repository);
    }

    @Bean
    public FindReviewsQuery findReviewsQuery(ReviewRepository repository) {
        return new FindReviewsQuery(repository);
    }
}
