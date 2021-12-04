package com.example.myproject.web;

import com.example.myproject.model.view.PictureViewModel;
import com.example.myproject.service.PictureService;
import com.example.myproject.service.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FacilityControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private RoomService roomService;

    @Test
    void getRoomsPage() throws Exception {
        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms"));
    }

    @Test
    void getDoubleRoomPage() throws Exception {
        mockMvc
                .perform(get("/rooms/double"))
                .andExpect(status().isOk())
                .andExpect(view().name("roomsDouble"));
    }

    @Test
    void getApartmentPage() throws Exception {
        mockMvc
                .perform(get("/rooms/apartment"))
                .andExpect(status().isOk())
                .andExpect(view().name("roomsApartment"));
    }

    @Test
    void getStudioPage() throws Exception {
        mockMvc
                .perform(get("/rooms/studio"))
                .andExpect(status().isOk())
                .andExpect(view().name("roomsStudio"));
    }

    @Test
    void getRestaurantPage() throws Exception {
        List<PictureViewModel> allRestaurantPictures = this.pictureService.getAllRestaurantPictures();
        mockMvc
                .perform(get("/restaurant"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurant"));
    }

    @Test
    void getAboutPage() throws Exception {
        mockMvc
                .perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    @Test
    void getParkingPage() throws Exception {
        mockMvc
                .perform(get("/parking"))
                .andExpect(status().isOk())
                .andExpect(view().name("parking"));
    }
}