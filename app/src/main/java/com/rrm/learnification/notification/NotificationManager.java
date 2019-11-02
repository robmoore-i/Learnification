package com.rrm.learnification.notification;

import com.rrm.learnification.response.NotificationTextContent;

public interface NotificationManager {
    void updateLatestWithReply(NotificationTextContent replyContent);

    boolean hasActiveLearnifications();
}
