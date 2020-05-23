package com.rrm.learnification.learnification.creation;

import com.rrm.learnification.learnification.publication.LearnificationTextGenerator;
import com.rrm.learnification.notification.IdentifiedNotification;

public class LearnificationFactory {
    private final LearnificationTextGenerator learnificationTextGenerator;
    private final LearnificationNotificationFactory notificationFactory;

    public LearnificationFactory(LearnificationTextGenerator learnificationTextGenerator, LearnificationNotificationFactory notificationFactory) {
        this.learnificationTextGenerator = learnificationTextGenerator;
        this.notificationFactory = notificationFactory;
    }

    public IdentifiedNotification getLearnification() {
        return notificationFactory.createLearnification(learnificationTextGenerator.learnificationText());
    }
}
