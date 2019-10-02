package com.rrm.learnification.learnification;

public interface NotificationManager {
    void cancelLatest();

    void updateLatestWithReply(ResponseNotificationContent replyContent);
}
