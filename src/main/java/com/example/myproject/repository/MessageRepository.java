package com.example.myproject.repository;

import com.example.myproject.model.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    @Query("select m From MessageEntity m")
    List<MessageEntity> getAllMessages();
}
