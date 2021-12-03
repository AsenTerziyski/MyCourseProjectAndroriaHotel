package com.example.myproject.web;

import com.example.myproject.model.view.PictureViewModel;
import com.example.myproject.model.view.RoomPricesView;
import com.example.myproject.service.PictureService;
import com.example.myproject.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FacilityController {
    private final PictureService pictureService;
    private final RoomService roomService;

    public FacilityController(PictureService pictureService, RoomService roomService) {
        this.pictureService = pictureService;
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public String getRoomsPage() {
        return "rooms";
    }

    @GetMapping("/rooms/double")
    public String getDoubleRoomPage(Model model) {
        List<PictureViewModel> allDoubleRoomPictures =
                this.pictureService.getAllDoubleRoomPictures();
        model.addAttribute("doubleRoomPictures", allDoubleRoomPictures);
        return "roomsDouble";
    }

    @GetMapping("/rooms/apartment")
    public String getApartmentPage(Model model) {
        List<PictureViewModel> allAppartmentPictures =
                this.pictureService.getAllApartmentPictures();
        model.addAttribute("apartmentPictures", allAppartmentPictures);
        return "roomsApartment";
    }

    @GetMapping("/rooms/studio")
    public String getStudioPage(Model model) {
        List<PictureViewModel> allStudioPictures =
                this.pictureService.getAllStudioPictures();
        model.addAttribute("studioPictures", allStudioPictures);
        return "roomsStudio";
    }

    @GetMapping("/restaurant")
    public String getRestaurantPage(Model model) {
        List<PictureViewModel> allRestaurantPictures = this.pictureService.getAllRestaurantPictures();
        model.addAttribute("restaurantPictures", allRestaurantPictures);
        return "restaurant";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "about";
    }

    @GetMapping("/parking")
    public String getParkingPage(Model model) {
        List<PictureViewModel> allParkingPictures = this.pictureService.getAllParkingPictures();
        model.addAttribute("allParkingPictures", allParkingPictures);
        return "parking";
    }


}
