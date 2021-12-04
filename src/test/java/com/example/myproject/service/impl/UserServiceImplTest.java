package com.example.myproject.service.impl;

import com.example.myproject.model.entities.UserEntity;
import com.example.myproject.model.entities.UserRoleEntity;
import com.example.myproject.model.entities.enums.UserRoleEnum;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.repository.UserRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository mockedUserRepository;
    @Mock
    private PasswordEncoder mockedPasswordEncoder;
    @Mock
    private UserRoleRepository mockedUserRoleRepository;
    @Mock
    private AndroriaUserServiceImpl mockedAndroriaUserService;

    private UserServiceImpl userServiceToTest;
    private UserEntity testEntity1;
    private UserEntity testEntity2;

    @BeforeEach
    void init() {

        UserRoleEntity admin = new UserRoleEntity();
        admin.setRole(UserRoleEnum.ADMIN);
        UserRoleEntity user = new UserRoleEntity();
        user.setRole(UserRoleEnum.USER);

        testEntity1 = new UserEntity();
        testEntity1.setUsername("usernameTest1").setPassword("passTest1")
                .setFullName("Test Test1").setRoles(List.of(admin,user));


        testEntity2 = new UserEntity();
        testEntity2.setUsername("usernameTest2").setPassword("passTest2")
                .setFullName("Test Test2").setRoles(List.of(admin,user));

    }

    @Test
    void findUserByUsernameAndPassword() {
        Mockito.when(this.mockedUserRepository.findByUsernameAndPassword(testEntity1.getUsername(), testEntity1.getPassword()))
                .thenReturn(Optional.ofNullable(testEntity1));
        initializeUserServiceToTest();
        boolean actual = userServiceToTest
                .findUserByUsernameAndPassword(testEntity1.getUsername(), testEntity1.getPassword());
        Assertions.assertTrue(actual);
        boolean actualWrong = userServiceToTest.findUserByUsernameAndPassword("wrong", "wrong");
        Assertions.assertFalse(actualWrong);

    }

    private void initializeUserServiceToTest() {
        this.userServiceToTest = new UserServiceImpl(mockedUserRepository,
                mockedPasswordEncoder,
                mockedUserRoleRepository,
                mockedAndroriaUserService);
    }

    @Test
    void findUserByUsername() {
        Mockito.when(this.mockedUserRepository.findByUsername(testEntity1.getUsername())).thenReturn(Optional.of(testEntity1));
        initializeUserServiceToTest();
        UserEntity userByUsername = userServiceToTest.findUserByUsername(testEntity1.getUsername());
        Assertions.assertTrue(userByUsername.getUsername().equals(testEntity1.getUsername()));

    }

    @Test
    void checkIfUsernameIsExists() {
        Mockito
                .when(this.mockedUserRepository.findByUsername(testEntity1.getUsername()))
                .thenReturn(Optional.of(testEntity1));
        initializeUserServiceToTest();
        boolean actual = this.userServiceToTest.checkIfUsernameExists(testEntity1.getUsername());
        Assertions.assertTrue(actual);
        boolean wrong = this.userServiceToTest.checkIfUsernameExists("wrong");
        Assertions.assertFalse(wrong);
    }

    @Test
    void userIsAdmin() {
        initializeUserServiceToTest();
        boolean userIsAdmin = userServiceToTest.userIsAdmin(testEntity1);
        Assertions.assertTrue(userIsAdmin);
    }

    @Test
    void findAllUsers() {
        List<UserEntity> testUsers = List.of(testEntity1, testEntity2);
        Mockito
                .when(this.mockedUserRepository.findAll()).thenReturn(testUsers);
        initializeUserServiceToTest();
        List<UserEntity> allUsers = userServiceToTest.findAllUsers();
        for (UserEntity user : allUsers) {
            Assertions.assertTrue(testUsers.contains(user));
        }
    }
}