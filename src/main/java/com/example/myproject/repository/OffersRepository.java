package com.example.myproject.repository;

import com.example.myproject.model.entities.OffersEntity;
import com.example.myproject.model.entities.enums.RoomEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffersRepository extends JpaRepository<OffersEntity, Long> {
//    List<OffersEntity> getAll();
    List<OffersEntity> getAllByRoomNotNull();
    OffersEntity findByRoom(RoomEnum room);

}
