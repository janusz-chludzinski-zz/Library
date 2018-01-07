package com.ohgianni.tin.Exception;

public class RecommendationNotFoundException extends Exception {

    public RecommendationNotFoundException() {
        super("Nie znaleziono wskazanej rekomendacji");
    }

}
