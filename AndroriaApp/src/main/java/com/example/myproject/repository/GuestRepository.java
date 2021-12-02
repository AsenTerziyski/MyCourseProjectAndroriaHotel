package com.example.myproject.repository;

import com.example.myproject.model.entities.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
    Optional<GuestEntity> findByEmail(String email);

    List<GuestEntity> findAllByMessagesIsNotNull();

    @Query("select g from GuestEntity g where g.messages.size>0")
    List<GuestEntity> findAllGuestWithMessages();

    @Query("select g from GuestEntity g where g.bookings.size>3")
    List<GuestEntity> findAllGuestsWithBookingsHigherThan3();

    List<GuestEntity> findAllByListOfBookingsIdsIsNotNull();
}
