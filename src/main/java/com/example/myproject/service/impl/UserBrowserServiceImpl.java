package com.example.myproject.service.impl;

import com.example.myproject.model.entities.UserBrowser;
import com.example.myproject.model.entities.UserEntity;
import com.example.myproject.repository.UserBrowserRepository;
import com.example.myproject.service.UserBrowserService;
import com.example.myproject.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserBrowserServiceImpl implements UserBrowserService {

    private final UserBrowserRepository userBrowserRepository;
    private final UserService userService;

    public UserBrowserServiceImpl(UserBrowserRepository userBrowserRepository, UserService userService) {
        this.userBrowserRepository = userBrowserRepository;
        this.userService = userService;
    }

    @Override
    public void saveUserBrowser(UserBrowser userBrowser) {
        LocalDate now = LocalDate.now();
        UserBrowser byLocalDateAndUsername = this.userBrowserRepository
                .findByLocalDateAndUsername(now, userBrowser.getUsername());
        if (byLocalDateAndUsername == null) {
            UserBrowser save = this.userBrowserRepository.save(userBrowser);
        }
    }

    @Override
    public List<UserBrowser> usersWhoBrowsedOn(LocalDate date) {
        List<UserBrowser> byDate = this.userBrowserRepository.findByLocalDate(date);
        return byDate;
    }

    @Override
    @Scheduled(cron = "${schedulers.cronDeleteBrowsingUsers}")
    public void scheduledDeleteOfBrowsingUsers() {
        if (this.userBrowserRepository.count() > 0) {
            this.userBrowserRepository.deleteAll();
        }
    }

}
