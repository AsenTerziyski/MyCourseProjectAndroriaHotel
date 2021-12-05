package com.example.myproject.repository;

import com.example.myproject.model.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> getAllByCheckOutAfter(LocalDate checkOut);
    List<BookingEntity> getAllByCheckOutBefore(LocalDate checkOut);

}
