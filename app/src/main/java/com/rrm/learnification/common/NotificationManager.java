package com.rrm.learnification.common;

interface NotificationManager {
    void cancelLatest();

    void updateLatestWithReply(ResponseNotificationContent replyContent);
}
