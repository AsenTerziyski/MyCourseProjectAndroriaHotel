package com.example.myproject.repository;

import com.example.myproject.model.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("select r from ReviewEntity r")
    List<ReviewEntity> findAllReviews();

    List<ReviewEntity> findAllByReviewerName(String reviewerName);
}
