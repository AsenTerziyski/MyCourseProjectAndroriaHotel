package com.example.myproject.web;

import com.example.myproject.model.entities.UserEntity;

import com.example.myproject.repository.UserRepository;
import com.example.myproject.service.UserBrowserService;
import com.example.myproject.service.UserService;
import com.example.myproject.service.impl.AndroriaUserServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WithMockUser("M@lmSuite")
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserBrowserService userBrowserService;
    @Autowired
    private AndroriaUserServiceImpl androriaUserService;
    @Autowired
    private ModelMapper modelMapper;

    private static final String TEST_USER_NAME = "testUser";
    private static final String TEST_USER_FULL_NAME = "Test Test";

    @AfterEach
    void tearDown() {
        userService.delete(TEST_USER_NAME);
    }

    @Test
    void testGetRegisterPage() throws Exception {
        mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("occupiedUsername", false))
                .andExpect(view().name("user-add"));
    }

    @Test
    void testGetLoginPage() throws Exception {
        mockMvc
                .perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testGetDeleteUserPage() throws Exception {
        mockMvc
                .perform(get("/user/delete-user"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-delete"));
    }

    @Test
    void registerPost() throws Exception {
        long countBefore = this.userService.findAllUsers().size();
        mockMvc
                .perform(post("/user/add-new-user")
                        .param("username", TEST_USER_NAME)
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("fullName", TEST_USER_FULL_NAME)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        List<UserEntity> all = this.userService.findAllUsers();
        Assertions.assertEquals(countBefore + 1, all.size());
        UserEntity userByUsername = this.userService.findUserByUsername(TEST_USER_NAME);
        Assertions.assertEquals(TEST_USER_NAME, userByUsername.getUsername());
        Assertions.assertTrue(passwordEncoder.matches("12345", userByUsername.getPassword()));
        Assertions.assertEquals(TEST_USER_FULL_NAME, userByUsername.getFullName());

        mockMvc
                .perform(post("/user/add-new-user")
                        .param("username", "userToDelete")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("fullName", TEST_USER_FULL_NAME)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        mockMvc
                .perform(post("/user/add-new-user")
                        .param("username", "u")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("fullName", TEST_USER_FULL_NAME)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("user-add"));

        mockMvc
                .perform(post("/user/add-new-user")
                        .param("username", "userUser")
                        .param("password", "12345")
                        .param("confirmPassword", "123")
                        .param("fullName", TEST_USER_FULL_NAME)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("user-add"));

        mockMvc
                .perform(post("/user/add-new-user")
                        .param("username", "admin")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("fullName", TEST_USER_FULL_NAME)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/register"));
    }

    @Test
    void testDeleteUser() throws Exception {

        long countBefore = this.userService.findAllUsers().size();
        System.out.println(countBefore);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/user/delete-user")
                        .param("username", "userToDelete")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("userDeletedConfirmation"));
        List<UserEntity> allUsers = this.userService.findAllUsers();
        Assertions.assertEquals(countBefore-1, allUsers.size());

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/user/delete-user")
                        .param("username", "u")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(model().hasErrors())
                .andExpect(status().isOk())
                .andExpect(view().name("user-delete"));

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/user/delete-user")
                        .param("username", "userToDeleteNotExisting")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(view().name("user-delete"));
    }

    @Test
    void testGetUsersWhoBrowsedToday() throws Exception {
        mockMvc
                .perform(get("/users-browser"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-browser"));
    }

}