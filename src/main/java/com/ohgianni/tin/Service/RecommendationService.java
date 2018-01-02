package com.ohgianni.tin.Service;

import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Recommendation;
import com.ohgianni.tin.Repository.BookRepository;
import com.ohgianni.tin.Repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Collections.sort;
import static java.util.Comparator.*;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final BookRepository bookRepository;

    private final HttpSession session;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository, BookRepository bookRepository, HttpSession session) {
        this.recommendationRepository = recommendationRepository;
        this.bookRepository = bookRepository;
        this.session = session;
    }

    @Transactional
    public Recommendation addRecommendation(Recommendation recommendation) {

        recommendation.setClient((Client)session.getAttribute("client"));
        recommendation.setRecommendationTime(now());
        recommendation.setVotes(1L);

        return recommendationRepository.save(recommendation);

    }

    public BindingResult validateRecommendation(Recommendation recommendation, BindingResult errors) {

        isReported(recommendation, errors);
        bookExists(recommendation, errors);

        return errors;
    }

    private void bookExists(Recommendation recommendation, BindingResult errors) {

//        List<Book> books = bookRepository.findByIsbn(recommendation.getIsbn());

        if(bookRepository.existsByIsbn(recommendation.getIsbn())) {
            errors.addError(new FieldError("alreadyExists", "isbn", "Książka o numerze ISBN " + recommendation.getIsbn() + " istnieje już w naszym zbiorze"));
        }

    }

    private void isReported(Recommendation recommendation, BindingResult errors) {

        recommendationRepository.findByIsbn(recommendation.getIsbn())
                .ifPresent(r -> errors.addError(new FieldError("alreadyReported", "isbn", "Książka o numerze ISBN " + r.getIsbn() + " została już zarekomednowana")));

    }

    private void addVote(Recommendation recommendation) {
        // TODO: 1/1/18
    }

    public List<Recommendation> findAll() {

        List<Recommendation> recommendations = recommendationRepository.findAll();
        recommendations.sort(comparing(Recommendation::getVotes).reversed());

        return recommendations;
    }
}
