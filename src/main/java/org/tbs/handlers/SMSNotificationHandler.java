package org.tbs.handlers;

import lombok.extern.slf4j.Slf4j;
import org.tbs.factory.NotificationStrategy;

@Slf4j
public class SMSNotificationHandler implements NotificationStrategy {
    @Override
    public void sendNotificationToUser(Long userId) {
        log.info("Send SMS notification to user {}", userId);

    }

    @Override
    public void sendNotificationToAdmin(Long adminID) {
        log.info("Send SMS notification to Admin {}", adminID);
    }
}
