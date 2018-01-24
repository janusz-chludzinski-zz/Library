package com.ohgianni.tin.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ohgianni.tin.Entity.Recommendation;

@org.springframework.stereotype.Repository
public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {

    Optional<Recommendation> findByIsbn(Long isbn);

    List<Recommendation> findAllByOrderByVotesDesc();
}
