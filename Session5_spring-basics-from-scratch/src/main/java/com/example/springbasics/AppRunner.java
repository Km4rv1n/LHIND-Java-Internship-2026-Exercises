package com.example.springbasics;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private final NotificationManager notificationManager;

    public AppRunner(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override
    public void run(String... args) throws Exception {
        notificationManager.notifyUser("Welcome to Spring!");
    }
}
