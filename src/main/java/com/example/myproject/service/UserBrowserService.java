package com.example.myproject.service;

import com.example.myproject.model.entities.UserBrowser;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserBrowserService {
    void saveUserBrowser(UserBrowser userBrowser);
    List<UserBrowser> usersWhoBrowsedOn(LocalDate date);
    void scheduledDeleteOfBrowsingUsers();
}
