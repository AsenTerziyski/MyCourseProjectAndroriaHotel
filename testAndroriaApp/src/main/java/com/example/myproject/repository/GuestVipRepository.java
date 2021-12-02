package com.example.myproject.repository;

import com.example.myproject.model.entities.GuestVipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestVipRepository extends JpaRepository<GuestVipEntity, Long> {
    GuestVipEntity findByOriginalId(Long id);
}
