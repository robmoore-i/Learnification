package com.rrm.learnification.notification;

public interface NotificationManager {
    void cancelLatest();

    void updateLatestWithReply(ResponseNotificationContent replyContent);
}
