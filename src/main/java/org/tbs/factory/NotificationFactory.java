package org.tbs.factory;

import org.tbs.enums.NotificationType;
import org.tbs.handlers.EmailNotificationHandler;
import org.tbs.handlers.SMSNotificationHandler;

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
