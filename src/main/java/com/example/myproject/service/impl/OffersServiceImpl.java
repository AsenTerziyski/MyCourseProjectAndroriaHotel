package com.example.myproject.service.impl;

import com.example.myproject.model.binding.OfferAddBindingModel;
import com.example.myproject.model.entities.OffersEntity;
import com.example.myproject.model.entities.RoomTypeEntity;
import com.example.myproject.model.entities.UserEntity;
import com.example.myproject.model.entities.enums.RoomEnum;
import com.example.myproject.model.view.OfferSummaryView;
import com.example.myproject.repository.OffersRepository;
import com.example.myproject.repository.RoomRepository;
import com.example.myproject.service.OffersService;
import com.example.myproject.service.RoomService;
import com.example.myproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OffersServiceImpl implements OffersService {
    private final OffersRepository offersRepository;
    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public OffersServiceImpl(OffersRepository offersRepository, RoomService roomService, RoomRepository roomRepository, ModelMapper modelMapper, UserService userService) {
        this.offersRepository = offersRepository;
        this.roomService = roomService;
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void initOffers() {

        if(this.offersRepository.count() == 0) {
            BigDecimal price = BigDecimal.valueOf(75);
            RoomEnum room = RoomEnum.DOUBLE_ROOM;
            OffersEntity offerDoubleRoom = new OffersEntity();
            offerDoubleRoom.setRoom(room);
            UserEntity admin = this.userService.findUserByUsername("admin");
            offerDoubleRoom.setUser(admin);
            offerDoubleRoom.setDiscount(10);
            offerDoubleRoom.setVipDiscount(15);
            offerDoubleRoom.setDescription("Lorem ipsum dolor sit amet, consectetur\n" +
                    "            adipiscing elit, sed do eiusmod tempor incididunt ut labore et\n" +
                    "            dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex\n" +
                    "            ea\n" +
                    "            commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat\n" +
                    "            nulla\n" +
                    "            pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id\n" +
                    "            est\n" +
                    "            laborum.");
            offerDoubleRoom.setStay(5);
            this.offersRepository.save(offerDoubleRoom);
        }

    }

    @Override
    public boolean isOfferOwner(Principal principal, Long id) {
        String name = principal.getName();
        Optional<OffersEntity> optionalOffersEntity = this.offersRepository.findById(id);
        UserEntity caller = this.userService.findUserByUsername(name);
        if (optionalOffersEntity.isEmpty() || caller == null) {
            return false;
        } else {
            UserEntity userWhoPostedOffer = optionalOffersEntity.get().getUser();
            boolean isAdmin = this.userService.userIsAdmin(caller);
            return isAdmin || principal.getName().equalsIgnoreCase(userWhoPostedOffer.getUsername());
        }
    }

    //    @Transactional
    @Override
    public boolean addOffer(OfferAddBindingModel offerAddBindingModel, Principal principal) {

        RoomEnum room = offerAddBindingModel.getRoom();
        OffersEntity byRoom = this.offersRepository.findByRoom(room);
        if (byRoom != null) {
            String username = principal.getName();
            UserEntity userByUsername = this.userService.findUserByUsername(username);
            byRoom.setUser(userByUsername);
            byRoom.setDiscount(offerAddBindingModel.getDiscount());
            byRoom.setVipDiscount(offerAddBindingModel.getVipDiscount());
            byRoom.setDescription(offerAddBindingModel.getDescription());
            byRoom.setStay(offerAddBindingModel.getStay());
            this.offersRepository.save(byRoom);
            return true;
        }

        OffersEntity newOffer = this.modelMapper.map(offerAddBindingModel, OffersEntity.class);
        String username = principal.getName();
        UserEntity userByUsername = this.userService.findUserByUsername(username);
        if (userByUsername != null) {
            newOffer.setUser(userByUsername);
            newOffer.setRoom(room);
            this.offersRepository.save(newOffer);
//            RoomTypeEntity roomBy = this.roomService.findRoomBy(room);
//            BigDecimal price = roomBy.getPrice();
//            roomBy.setPrice(price);
//            this.roomRepository.save(roomBy);
            return true;
        }
        return false;
    }

    @Override
    public List<OfferSummaryView> getAllOffers() {
        List<OffersEntity> all = this.offersRepository.findAll();
        List<OfferSummaryView> collect = all.stream().map(offersEntity -> {
            OfferSummaryView map = this.modelMapper.map(offersEntity, OfferSummaryView.class);
            String name = offersEntity.getRoom().name();
            if (name.equals("DOUBLE_ROOM")) {
                name = "DOUBLE ROOM";
            }
            map.setRoom(name);
            String addedBy = offersEntity.getUser().getUsername();
            map.setAddedBy(addedBy);
            return map;
        }).collect(Collectors.toList());
//        List<OfferSummaryView> collect = all
//                .stream()
//                .map(offersEntity -> this.modelMapper.map(offersEntity, OfferSummaryView.class))
//                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public boolean removeOffer(Long id) {
        this.offersRepository.deleteById(id);
        return false;
    }

    @Override
    public OffersEntity findOfferByRoomType(RoomTypeEntity roomType) {
        RoomEnum type = roomType.getType();
        OffersEntity byRoom = this.offersRepository.findByRoom(type);

        return byRoom;
    }


}
