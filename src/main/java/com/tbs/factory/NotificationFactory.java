package com.tbs.factory;

import com.tbs.enums.NotificationType;
import com.tbs.handlers.EmailNotificationHandler;
import com.tbs.handlers.SMSNotificationHandler;

import java.util.HashMap;
import java.util.Map;

public class NotificationFactory {
    private Map<NotificationType, NotificationStrategy> typeToHandler = new HashMap<>();

    public NotificationFactory() {
        typeToHandler.put(NotificationType.EMAIL, new EmailNotificationHandler());
        typeToHandler.put(NotificationType.SMS, new SMSNotificationHandler());
    }

    public void sendNotification(NotificationType notificationType, Long userId) {
        typeToHandler.get(notificationType).sendNotificationToUser(userId);
    }
}
