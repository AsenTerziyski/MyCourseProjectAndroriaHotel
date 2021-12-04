package com.example.myproject.service.impl;

import com.example.myproject.model.entities.GuestEntity;
import com.example.myproject.model.entities.GuestVipEntity;
import com.example.myproject.repository.GuestVipRepository;
import com.example.myproject.service.GuestService;
import com.example.myproject.service.GuestVipService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GuestVipServiceImpl implements GuestVipService {
    private final GuestService guestService;
    private final GuestVipRepository guestVipRepository;


    public GuestVipServiceImpl(GuestService guestService, GuestVipRepository guestVipRepository) {
        this.guestService = guestService;
        this.guestVipRepository = guestVipRepository;
    }

    @Override
    @Scheduled(cron = "${schedulers.cronAddVipGuest}")
    public void extractAllVipGuests() {

        List<GuestEntity> all = this.guestService.findAllGuestsWhoHaveBookings();

        for (GuestEntity guestEntity : all) {
            int length = guestEntity.getListOfBookingsIds().trim().split(" ").length;
            if (length > 3) {
                Long originalId = guestEntity.getId();
                GuestVipEntity byOriginalId = this.guestVipRepository.findByOriginalId(originalId);
                if (byOriginalId != null) {
                    byOriginalId
                            .setUsername(guestEntity.getUsername())
                            .setEmail(guestEntity.getEmail())
                            .setOriginalId(guestEntity.getId())
                            .setNumberOfBookings(length)
                            .setBookingsListId(guestEntity.getListOfBookingsIds());
                    this.guestVipRepository.save(byOriginalId);
                } else {
                    GuestVipEntity newVip = new GuestVipEntity();
                    newVip
                            .setUsername(guestEntity.getUsername())
                            .setEmail(guestEntity.getEmail())
                            .setBookingsListId(guestEntity.getListOfBookingsIds())
                            .setOriginalId(guestEntity.getId())
                            .setNumberOfBookings(length);
                    this.guestVipRepository.save(newVip);
                }
            }

        }

    }

    @Override
    public boolean findIfGuestIsVip(Long id) {
        GuestVipEntity byOriginalId = this.guestVipRepository.findByOriginalId(id);
        if (byOriginalId == null) {
            return false;
        }
        return true;
    }
}
