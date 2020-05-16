package com.rrm.learnification.notification;

import com.rrm.learnification.response.NotificationTextContent;

public interface ResponseNotificationCorrespondent {
    void updateLatestWithReply(NotificationTextContent replyContent, String learningItemPrompt, String expectedUserResponse);

    boolean hasActiveLearnifications();
}
