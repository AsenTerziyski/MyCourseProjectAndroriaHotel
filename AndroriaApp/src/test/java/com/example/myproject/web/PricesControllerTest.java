package com.example.myproject.web;

//import com.example.myproject.model.entities.enums.RoomEnum;
import com.example.myproject.model.binding.PricesEditBindingModel;
import com.example.myproject.model.entities.enums.RoomEnum;
import com.example.myproject.service.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Enumeration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WithMockUser(username = "tuser", roles = {"USER"})
@SpringBootTest
@AutoConfigureMockMvc
class PricesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoomService roomService;


    @Test
    void testGetPricesPage() throws Exception {
        mockMvc
                .perform(get("/prices"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allRoomsPrices"))
                .andExpect(view().name("prices-all"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getEditPricesPage() throws Exception {
        mockMvc
                .perform(get("/prices/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("prices-edit"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER", "ADMIN"})
    void testEditPrices() throws Exception {

        BigDecimal priceBeforeTest = this.roomService
                .findRoomBy(RoomEnum.STUDIO).getPrice();

        String expected = BigDecimal.valueOf(25000).toString();
        mockMvc.perform(post("/prices/edit/post-price")
                        .param("room", RoomEnum.STUDIO.toString())
                        .param("price", expected)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("priceEditConfirmation"));
        String actual = String.format("%.0f", this.roomService.findRoomBy(RoomEnum.STUDIO).getPrice());
        Assertions.assertEquals(expected, actual);

        returnPriceBeforeTest(priceBeforeTest);

        mockMvc.perform(post("/prices/edit/post-price")
                        .param("room", "WRONG ROOM TYPE")
                        .param("price", expected)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().hasErrors())
                .andExpect(view().name("prices-edit"));
    }

    private void returnPriceBeforeTest(BigDecimal priceBeforeTest) {
        PricesEditBindingModel pricesEditBindingModel = new PricesEditBindingModel();
        pricesEditBindingModel.setPrice(priceBeforeTest).setRoom(RoomEnum.STUDIO);
        this.roomService.editPrice(pricesEditBindingModel);
    }
}