package com.example.myproject.service;

import com.example.myproject.model.binding.OfferAddBindingModel;
import com.example.myproject.model.entities.OffersEntity;
import com.example.myproject.model.entities.RoomTypeEntity;
import com.example.myproject.model.view.OfferSummaryView;

import java.security.Principal;
import java.util.List;

public interface OffersService {
    boolean addOffer(OfferAddBindingModel offerAddBindingModel, Principal principal);

    List<OfferSummaryView> getAllOffers();

    boolean removeOffer(Long id);

    OffersEntity findOfferByRoomType(RoomTypeEntity roomType);

    void initOffers();

    boolean isOfferOwner(Principal principal, Long id);
}
