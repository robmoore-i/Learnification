package com.rrm.learnification.learnificationresponse.publication;

import com.rrm.learnification.learnification.response.NotificationTextContent;

public interface LearnificationUpdater {
    void updateLatestWithReply(NotificationTextContent replyContent, String learningItemPrompt, String expectedUserResponse);
}
