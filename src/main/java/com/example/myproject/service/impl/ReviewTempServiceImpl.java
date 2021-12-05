package com.example.myproject.service.impl;

import com.example.myproject.model.entities.ReviewTempEntity;
import com.example.myproject.repository.ReviewTempRepository;
import com.example.myproject.service.ReviewTempService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewTempServiceImpl implements ReviewTempService {

    private final ReviewTempRepository reviewTempRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewTempServiceImpl.class);

    public ReviewTempServiceImpl(ReviewTempRepository reviewTempRepository) {
        this.reviewTempRepository = reviewTempRepository;
    }

    @Override
    @Scheduled(cron = "${schedulers.cronReviewsTemp}")
    public void deleteAllTempReviews() {
        if (this.reviewTempRepository.count() > 0) {
            List<ReviewTempEntity> all = this.reviewTempRepository.findAll();
            for (ReviewTempEntity reviewTempEntity : all) {
                LOGGER.info("Deleted tempReview by guest with original id: {}", reviewTempEntity.getOriginalId());
            }
            this.reviewTempRepository.deleteAll();
        }
    }

    @Override
    public void addTempReview(ReviewTempEntity reviewTempEntity) {
        this.reviewTempRepository.save(reviewTempEntity);
    }
}
