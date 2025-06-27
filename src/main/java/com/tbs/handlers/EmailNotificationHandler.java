package com.tbs.handlers;

import lombok.extern.slf4j.Slf4j;
import com.tbs.factory.NotificationStrategy;

@Slf4j
public class EmailNotificationHandler implements NotificationStrategy {
    @Override
    public void sendNotificationToUser(Long userId) {
        log.info("Send Email notification to user {}", userId);
    }

    @Override
    public void sendNotificationToAdmin(Long adminID) {
        log.info("Send Email notification to Admin {}", adminID);
    }
}
