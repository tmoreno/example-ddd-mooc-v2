package com.tmoreno.mooc.backoffice.review.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewDaoJpaRepository extends JpaRepository<ReviewJpaDto, String> {

}
