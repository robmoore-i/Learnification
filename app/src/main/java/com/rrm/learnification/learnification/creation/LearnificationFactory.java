package com.rrm.learnification.learnification.creation;

import android.app.Notification;

import com.rrm.learnification.learnification.publication.LearnificationTextGenerator;

public class LearnificationFactory {
    private final LearnificationTextGenerator learnificationTextGenerator;
    private final LearnificationNotificationFactory notificationFactory;

    public LearnificationFactory(LearnificationTextGenerator learnificationTextGenerator, LearnificationNotificationFactory notificationFactory) {
        this.learnificationTextGenerator = learnificationTextGenerator;
        this.notificationFactory = notificationFactory;
    }

    public Notification getLearnification() {
        return notificationFactory.createLearnification(learnificationTextGenerator.learnificationText());
    }
}
