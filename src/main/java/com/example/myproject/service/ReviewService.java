package com.example.myproject.service;

import com.example.myproject.model.view.ReviewSummeryView;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewSummeryView> getAllReviews();

    ReviewSummeryView getReviewById(Long id);

    boolean deleteReviewById(Long id);

    void initReviews();

    void deleteAllReviewsByAnonymous();

}
