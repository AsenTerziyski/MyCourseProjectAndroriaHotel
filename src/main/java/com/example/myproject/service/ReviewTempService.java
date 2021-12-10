package com.example.myproject.service;

import com.example.myproject.model.entities.ReviewTempEntity;

public interface ReviewTempService {
    void deleteAllTempReviews();

    void addTempReview(ReviewTempEntity reviewTempEntity);
}
