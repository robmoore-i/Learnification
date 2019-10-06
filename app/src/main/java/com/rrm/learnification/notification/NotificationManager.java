package com.rrm.learnification.notification;

import com.rrm.learnification.response.ResponseNotificationContent;

public interface NotificationManager {
    void cancelLatest();

    void updateLatestWithReply(ResponseNotificationContent replyContent);

    boolean hasActiveLearnifications();
}
