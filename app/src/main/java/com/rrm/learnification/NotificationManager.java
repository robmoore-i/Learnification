package com.rrm.learnification;

interface NotificationManager {
    void cancelLatest();

    void updateLatestWithReply(ResponseNotificationContent replyContent);
}
