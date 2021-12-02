package com.example.myproject.web;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetHomePage() throws Exception {
        mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index_androria"));
    }
}