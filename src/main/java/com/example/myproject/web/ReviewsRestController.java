package com.example.myproject.web;

import com.example.myproject.model.view.ReviewSummeryView;
import com.example.myproject.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/androriapi")
public class ReviewsRestController {
    private final ReviewService reviewService;

    public ReviewsRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewSummeryView>> getAllReviews() {
        List<ReviewSummeryView> allReviews = this.reviewService.getAllReviews();
        return ResponseEntity.ok(allReviews);
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<ReviewSummeryView> getReviewById(@PathVariable("id") Long id) {

        ReviewSummeryView reviewById = this.reviewService.getReviewById(id);
        if (reviewById == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(reviewById);
        }
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<ReviewSummeryView> deleteReview(@PathVariable("id") Long id) {
        System.out.println();
        boolean isDeleted = this.reviewService.deleteReviewById(id);
        return ResponseEntity.noContent().build();
    }

}
