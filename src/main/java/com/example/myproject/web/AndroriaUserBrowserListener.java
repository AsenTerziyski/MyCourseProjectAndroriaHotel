package com.example.myproject.web;

import com.example.myproject.model.entities.UserBrowser;
import com.example.myproject.service.UserBrowserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import java.time.LocalDate;
import java.util.Locale;

@Component
public class AndroriaUserBrowserListener {
    private final UserBrowserService userBrowserService;
    private Logger LOGGER = LoggerFactory.getLogger(AndroriaUserBrowserListener.class);
    private String userName;

    public AndroriaUserBrowserListener(UserBrowserService userBrowserService) {
        this.userBrowserService = userBrowserService;
    }


    @EventListener(ServletRequestHandledEvent.class)
    public void onApplicationEvent(ServletRequestHandledEvent event) {

        String userName = event.getUserName();
        LocalDate now = LocalDate.now();

        String name = modifyName(userName);
            LOGGER.info("Event: {}", event);
            UserBrowser userBrowser = new UserBrowser();
            userBrowser.setUsername(name.toUpperCase(Locale.ROOT));
            userBrowser.setLocalDate(now);
            this.userBrowserService.saveUserBrowser(userBrowser);
    }

    public String modifyName(String userName) {
        if (userName == null) {
            return "ANONYMOUS";
        } else if (userName.equalsIgnoreCase("admin")) {
            return "ADMIN";
        } else {
            return userName.toUpperCase(Locale.ROOT);
        }
    }

}
