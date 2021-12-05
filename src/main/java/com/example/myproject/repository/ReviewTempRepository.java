package com.example.myproject.repository;

import com.example.myproject.model.entities.ReviewTempEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewTempRepository extends JpaRepository<ReviewTempEntity, Long> {
}
