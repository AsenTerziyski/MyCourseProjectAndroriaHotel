package com.example.myproject.service.impl;

import com.example.myproject.model.entities.UserEntity;
import com.example.myproject.model.entities.UserRoleEntity;
import com.example.myproject.model.entities.enums.UserRoleEnum;
import com.example.myproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AndroriaUserServiceImplTest {
    private AndroriaUserServiceImpl serviceToTest;
    private UserEntity testUserAdmin;
    private UserEntity testUserNotAdmin;
    private UserRoleEntity testRoleAdmin;
    private UserRoleEntity testRoleUser;

    @Mock
    private UserRepository mockedUserRepository;

    @BeforeEach
    void init() {
        serviceToTest = new AndroriaUserServiceImpl(mockedUserRepository);

        testRoleAdmin = new UserRoleEntity();
        testRoleAdmin.setRole(UserRoleEnum.ADMIN);

        testRoleUser = new UserRoleEntity();
        testRoleUser.setRole(UserRoleEnum.USER);

        testUserAdmin = new UserEntity();
        testUserAdmin
                .setFullName("Test Testov")
                .setAdmin(true)
                .setUsername("admincho")
                .setRoles(List.of(testRoleAdmin, testRoleUser))
                .setPassword("test_pass");

        testUserNotAdmin = new UserEntity();
        testUserNotAdmin
                .setFullName("Test Testov")
                .setAdmin(true)
                .setUsername("NotAdmincho")
                .setRoles(List.of(testRoleUser))
                .setPassword("test_pass");
    }

    @Test
    void testUserNotFoundShouldThrowUsernameNotFoundExceptionIfUserNotExisting() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("wrongUsername"));
    }

    @Test
    void testUserFound() {
        Mockito
                .when(mockedUserRepository.findByUsername(testUserAdmin.getUsername()))
                .thenReturn(Optional.of(testUserAdmin));

        UserDetails actual = serviceToTest.loadUserByUsername(testUserAdmin.getUsername());

        String actualUsername = actual.getUsername();
        String expected = testUserAdmin.getUsername();

        Assertions.assertEquals(actualUsername, expected);

        String actualRoles = actual
                .getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(" "));

        String expectedRoles = "ROLE_ADMIN ROLE_USER";
        assertEquals(actualRoles, expectedRoles);

        Mockito
                .when(mockedUserRepository.findByUsername(testUserNotAdmin.getUsername()))
                .thenReturn(Optional.of(testUserNotAdmin));

        actual = serviceToTest.loadUserByUsername(testUserNotAdmin.getUsername());

        actualRoles = actual
                .getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(" "));
        expectedRoles = "ROLE_USER";
        assertEquals(actualRoles, expectedRoles);

    }





}