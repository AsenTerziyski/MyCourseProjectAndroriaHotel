package com.example.myproject.repository;

import com.example.myproject.model.entities.RoomTypeEntity;
import com.example.myproject.model.entities.enums.RoomEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomTypeEntity, Long> {
    Optional<RoomTypeEntity> findByType(RoomEnum type);
}
