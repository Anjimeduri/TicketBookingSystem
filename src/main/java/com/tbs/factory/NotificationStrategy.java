package com.tbs.factory;

public interface NotificationStrategy {
    void sendNotificationToUser(Long userId);

    void sendNotificationToAdmin(Long adminID);
}
