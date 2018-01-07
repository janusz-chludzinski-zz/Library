package com.ohgianni.tin.Service;

import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Recommendation;
import com.ohgianni.tin.Repository.BookRepository;
import com.ohgianni.tin.Repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

import static java.time.LocalDateTime.now;

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
        if(bookRepository.existsByIsbn(recommendation.getIsbn())) {
            errors.addError(new FieldError("isbn", "isbn", "Książka o numerze ISBN " + recommendation.getIsbn() + " istnieje już w naszym zbiorze"));
        }
    }

    private void isReported(Recommendation recommendation, BindingResult errors) {
        recommendationRepository.findByIsbn(recommendation.getIsbn())
                .ifPresent(r -> errors.addError(new FieldError("isbn", "isbn", "Książka o numerze ISBN " + r.getIsbn() + " została już zarekomednowana")));

    }

    @Transactional
    public void addVote(Long id, HttpSession session, RedirectAttributes redirectAttributes) {

        Client client = (Client) session.getAttribute("client");

            recommendationRepository.findById(id).ifPresent(recommendation -> {
                validateVote(client, recommendation, redirectAttributes);

                if(redirectAttributes.getFlashAttributes().containsKey("error")) {
                    return;
                }

                recommendation.setVotes(recommendation.getVotes() + 1);
                recommendation.addVoter(client);
                client.addVotedRecommendation(recommendation);

                recommendationRepository.save(recommendation);

            });
    }

    private void validateVote(Client client, Recommendation recommendation, RedirectAttributes redirectAttributes) {
        if(hasRecommendedThis(client, recommendation)) {
            redirectAttributes.addFlashAttribute("error", "Nie możesz głosować na książkę, którą rekomendowałeś");
        }
        else if(hasVotedAlready(client, recommendation)) {
            redirectAttributes.addFlashAttribute("error", "Nie możesz drugi raz głosować na tę samą książkę");
        }
        else if(hasVotedThreeTimes(client)) {
            redirectAttributes.addFlashAttribute("error","Nie możesz zagłosować na więcej niż 3 tytuły");
        }
    }

    private boolean hasVotedThreeTimes(Client client) {
        return client.getVotedRecommendations().size() >= 3;
    }

    private boolean hasVotedAlready(Client client, Recommendation recommendation) {
        return client.getVotedRecommendations().contains(recommendation);
    }

    public List<Recommendation> findAll() {
        return recommendationRepository.findAllByOrderByVotesDesc();
    }

    private boolean hasRecommendedThis(Client client, Recommendation recommendation) {
        return recommendation.getClient().equals(client);
    }


}
