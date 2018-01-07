package com.ohgianni.tin.Repository;

import com.ohgianni.tin.Entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {

    Optional<Recommendation> findByIsbn(Long isbn);

    List<Recommendation> findAllByOrderByVotesDesc();
}
