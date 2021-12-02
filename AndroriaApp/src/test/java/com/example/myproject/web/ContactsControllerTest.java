package com.example.myproject.web;

import com.example.myproject.model.entities.*;
import com.example.myproject.repository.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.myproject.model.entities.enums.RoomEnum;
import com.example.myproject.model.view.OfferSummaryView;
import com.example.myproject.service.OffersService;
import com.example.myproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.myproject.model.binding.ReviewSendBindingModel;
import com.example.myproject.model.view.ReviewSummeryView;
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
class ContactsControllerTest {

    @Autowired
    private GuestService guestService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getContacts() throws Exception {
        mockMvc
                .perform(get("/contacts"))
                .andExpect(status().isOk()).andExpect(view().name("contacts"));
    }

    @Test
    void testSendEmail() throws Exception {
        int messageNumberBefore = this.messageRepository.findAll().size();
        mockMvc
                .perform(post("/contacts/send")
                        .param("email", "test@test.com")
                        .param("text", "testMessageTestMessage")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("index_androria"));

        int messageNumberAfter = this.messageRepository.findAll().size();
        Assertions.assertEquals(messageNumberBefore + 1, messageNumberAfter);
        deleteTestMessage();
        mockMvc
                .perform(post("/contacts/send")
                        .param("email", "test@test.com")
                        .param("text", "t")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(model().hasErrors())
                .andExpect(view().name("contacts"));
    }

    private void deleteTestMessage() {
        int size = this.messageRepository.findAll().size();
        for (int i = size - 1; i >= 0; i--) {
            if (i == size - 1) {
                MessageEntity messageEntity = this.messageRepository.findAll().get(i);
                this.messageRepository.delete(messageEntity);
                break;
            }
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getContactFormMessagesPage() throws Exception {
        mockMvc
                .perform(get("/messages"))
                .andExpect(model().attributeExists("allMessages"))
                .andExpect(status().isOk()).andExpect(view().name("messages"));

    }
}