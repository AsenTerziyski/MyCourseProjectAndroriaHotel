package com.example.myproject.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class ReviewTempEntity extends BaseEntity{

    private Long originalId;
    private String reviewerName;
    private String reviewText;

    public ReviewTempEntity() {
    }

    public Long getOriginalId() {
        return originalId;
    }

    public ReviewTempEntity setOriginalId(Long originalId) {
        this.originalId = originalId;
        return this;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public ReviewTempEntity setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
        return this;
    }

//    @Column(columnDefinition = "LONGTEXT")
    @Lob
    public String getReviewText() {
        return reviewText;
    }

    public ReviewTempEntity setReviewText(String reviewText) {
        this.reviewText = reviewText;
        return this;
    }
}
