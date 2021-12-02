package com.example.myproject.repository;

import com.example.myproject.model.entities.PictureEntity;
import com.example.myproject.model.entities.enums.FacilityEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

    void deleteAllByPublicId(String publicId);
    List<PictureEntity> getAllByFacility(FacilityEnum facility);
}
