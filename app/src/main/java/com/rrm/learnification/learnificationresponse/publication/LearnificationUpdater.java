package com.rrm.learnification.learnificationresponse.publication;

import com.rrm.learnification.learnification.response.NotificationTextContent;

public interface LearnificationUpdater {
    void updateWithResponse(NotificationTextContent replyContent, String learningItemPrompt, String expectedUserResponse, int notificationId);
}
