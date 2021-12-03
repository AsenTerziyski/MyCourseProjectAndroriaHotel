package com.example.myproject.repository;

import com.example.myproject.model.entities.UserBrowser;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserBrowserRepository extends JpaRepository<UserBrowser, Long> {
    List<UserBrowser> findByLocalDate(LocalDate localDate);
    UserBrowser findByLocalDateAndUsername(LocalDate localDate, String username);
}
