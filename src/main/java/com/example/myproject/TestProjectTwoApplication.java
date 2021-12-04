package com.example.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TestProjectTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestProjectTwoApplication.class, args);
    }

}
