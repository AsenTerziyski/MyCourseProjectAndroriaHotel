package com.example.myproject.service;

public interface GuestVipService {
    void extractAllVipGuests();

    boolean findIfGuestIsVip(Long id);
}
