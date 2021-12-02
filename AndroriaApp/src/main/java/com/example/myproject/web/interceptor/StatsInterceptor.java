package com.example.myproject.web.interceptor;

import com.example.myproject.service.StatsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class StatsInterceptor implements HandlerInterceptor {
    private final StatsService statsService;

    public StatsInterceptor(StatsService statsService) {
        this.statsService = statsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        statsService.onRequest();
        return true;
    }
}
