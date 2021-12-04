package com.example.myproject.service;

import com.example.myproject.model.view.StatsView;

public interface StatsService {
    void onRequest();
    StatsView getStats();
}
