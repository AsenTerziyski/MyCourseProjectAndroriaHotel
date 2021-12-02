package com.example.myproject.web;

import com.example.myproject.model.entities.OffersEntity;
import com.example.myproject.model.entities.RoomTypeEntity;
import com.example.myproject.model.entities.UserEntity;
import com.example.myproject.model.entities.enums.RoomEnum;
import com.example.myproject.model.view.OfferSummaryView;
import com.example.myproject.repository.OffersRepository;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.service.OffersService;
import com.example.myproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.myproject.model.binding.ReviewSendBindingModel;
import com.example.myproject.model.entities.ReviewEntity;
import com.example.myproject.model.view.ReviewSummeryView;
import com.example.myproject.repository.ReviewRepository;
import com.example.myproject.service.GuestService;
import com.example.myproject.service.ReviewService;
import com.example.myproject.web.exceptions.UserNotSupportedOperation;
import net.bytebuddy.implementation.bytecode.Throw;
import org.aspectj.lang.annotation.AfterThrowing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OffersControllerTest {
    @Autowired
    private OffersService offersService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OffersRepository offersRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "user")
    void testGetOfferAddPage() throws Exception {
        mockMvc
                .perform(get("/add-offer"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("standardDiscountBiggerThanVip"))
                .andExpect(model().attribute("standardDiscountBiggerThanVip", false))
                .andExpect(view().name("offer-add"));
    }

    @Test
    @WithMockUser(username = "tuser", roles = {"USER"})
    void offerAddBindingModel() throws Exception {
        OffersEntity beforeTest = this.offersRepository.findByRoom(RoomEnum.STUDIO);
        mockMvc
                .perform(post("/add-offer")
                        .param("room", RoomEnum.STUDIO.toString())
                        .param("discount", String.valueOf(4.0))
                        .param("vipDiscount", String.valueOf(5.0))
                        .param("stay", String.valueOf(5L))
                        .param("description", "TEST STUDIO OFFER-TEST STUDIO OFFER-TEST STUDIO OFFER")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("index_androria"));

        OffersEntity actualStudioOffer = this.offersService.findOfferByRoomType(new RoomTypeEntity().setType(RoomEnum.STUDIO));
        double discount = actualStudioOffer.getDiscount();
        Assertions.assertEquals(discount, 4);
        double vipDiscount = actualStudioOffer.getVipDiscount();
        Assertions.assertEquals(vipDiscount, 5);
        long stay = actualStudioOffer.getStay();
        Assertions.assertEquals(stay, 5L);
        String username = actualStudioOffer.getUser().getUsername();
        Assertions.assertEquals(username, "tuser");
        String description = actualStudioOffer.getDescription();
        Assertions.assertEquals(description, "TEST STUDIO OFFER-TEST STUDIO OFFER-TEST STUDIO OFFER");
        RoomEnum room = actualStudioOffer.getRoom();
        Assertions.assertEquals(room.name(), "STUDIO");
        clearTestOffer(beforeTest);

        mockMvc
                .perform(post("/add-offer")
                        .param("room", "wrong room")
                        .param("discount", String.valueOf(4.0))
                        .param("vipDiscount", String.valueOf(5.0))
                        .param("stay", String.valueOf(5L))
                        .param("description", "TEST STUDIO OFFER-TEST STUDIO OFFER-TEST STUDIO OFFER")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(model().hasErrors())
                .andExpect(status().isOk())
                .andExpect(view().name("offer-add"));

        mockMvc
                .perform(post("/add-offer")
                        .param("room", RoomEnum.STUDIO.toString())
                        .param("discount", String.valueOf(5.0))
                        .param("vipDiscount", String.valueOf(1.0))
                        .param("stay", String.valueOf(5L))
                        .param("description", "!!! TEST-TEST-TEST!!!")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/add-offer"));
    }

    private void clearTestOffer(OffersEntity beforeTest) {
        if (beforeTest != null) {
            this.offersRepository.save(beforeTest);
        } else {
            OffersEntity byRoom = this.offersRepository.findByRoom(RoomEnum.STUDIO);
            this.offersRepository.delete(byRoom);
        }
    }

    @Test
    void testAllOffers() throws Exception {

        mockMvc
                .perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("offers"))
                .andExpect(model().attributeExists("noOffers"))
                .andExpect(status().isOk())
                .andExpect(view().name("offers-all"));

        List<OffersEntity> allOffers = this.offersRepository.findAll();
        this.offersRepository.deleteAll();

        mockMvc
                .perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("offers"))
                .andExpect(model().attribute("noOffers", true))
                .andExpect(status().isOk())
                .andExpect(view().name("offers-all"));

        this.offersRepository.saveAll(allOffers);
    }

    @Test
    @WithMockUser(username = "tuser", roles = {"USER"})
    void testGetRemoveOfferPage() throws Exception {
        mockMvc
                .perform(get("/remove-offer"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("offers"))
                .andExpect(status().isOk())
                .andExpect(view().name("offer-remove"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void testRemoveOffer() throws Exception {
        OffersEntity testOffer = new OffersEntity();
        UserEntity testUser = this.userService.findUserByUsername("tuser");
        Long id = 0L;

        if (testUser == null) {
            testUser = this.userRepository.save(testUser);
            id = testUser.getId();
        }

        testOffer.setUser(testUser)
                .setRoom(RoomEnum.STUDIO).setStay(5)
                .setVipDiscount(5)
                .setDiscount(4)
                .setDescription("testTestTestTest");

        Long idTest = this.offersRepository.save(testOffer).getId();

        mockMvc
                .perform(MockMvcRequestBuilders.post("/offers/remove/" + idTest)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());
        OffersEntity deletedOfferIsNull = this.offersRepository.findById(idTest).orElse(null);
        assertNull(deletedOfferIsNull);
    }

    @Test
    @WithMockUser(username = "malmsuite")
    void testRemoveOfferByNotRegisteredUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/offers/remove/" + 2)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is4xxClientError());
    }
}