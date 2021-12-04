package com.example.myproject.service.impl;

import com.example.myproject.model.entities.UserBrowser;
import com.example.myproject.model.entities.UserEntity;
import com.example.myproject.repository.UserBrowserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserBrowserServiceImplTest {

    @Mock
    private UserBrowserRepository mockedUserBrowserRepository;
    @Mock
    private UserServiceImpl mockedUserService;
    private UserBrowserServiceImpl userBrowserServiceToTest;

    private UserBrowser testUserBrowser1;
    private UserBrowser testUserBrowser2;

    @BeforeEach
    void init() {
        testUserBrowser1 = new UserBrowser();
        testUserBrowser1.setLocalDate(LocalDate.now()).setUsername("test1");
        testUserBrowser2 = new UserBrowser();
        testUserBrowser2.setLocalDate(LocalDate.now()).setUsername("test2");

    }

    private void initUserBrowserServiceToTest() {
        userBrowserServiceToTest = new UserBrowserServiceImpl(mockedUserBrowserRepository, mockedUserService);
    }

    @Test
    public void testUsersWhoBrowsedOn() {
        LocalDate now = LocalDate.now();
        List<UserBrowser> usersWhoBrowsedToday = List.of(testUserBrowser1, testUserBrowser2);
        Mockito.when(this.mockedUserBrowserRepository.findByLocalDate(now)).thenReturn(usersWhoBrowsedToday);
        initUserBrowserServiceToTest();
        List<UserBrowser> userBrowsers = userBrowserServiceToTest.usersWhoBrowsedOn(now);
        for (UserBrowser user : userBrowsers) {
            Assertions.assertTrue(usersWhoBrowsedToday.contains(user));
        }
    }

    @Test
    void testScheduledDeleteOfBrowsingUsers() {
        Mockito.when(this.mockedUserBrowserRepository.count()).thenReturn(2L);
        initUserBrowserServiceToTest();
        userBrowserServiceToTest.scheduledDeleteOfBrowsingUsers();
        Assertions.assertEquals(this.mockedUserBrowserRepository.count()-2, 0);
    }

}