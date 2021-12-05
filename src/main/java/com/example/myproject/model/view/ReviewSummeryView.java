package com.example.myproject.model.view;

import com.example.myproject.model.entities.GuestEntity;

public class ReviewSummeryView {
    private Long id;
    private String reviewerName;
    private String reviewText;

    public Long getId() {
        return id;
    }

    public ReviewSummeryView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public ReviewSummeryView setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
        return this;
    }

    public String getReviewText() {
        return reviewText;
    }

    public ReviewSummeryView setReviewText(String reviewText) {
        this.reviewText = reviewText;
        return this;
    }
}
