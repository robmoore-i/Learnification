package com.rrm.learnification.learnification;

import com.rrm.learnification.notification.ResponseNotificationContent;

public class LearnificationResponseContentGenerator {
    private final ScheduleConfiguration scheduleConfiguration;

    LearnificationResponseContentGenerator(ScheduleConfiguration scheduleConfiguration) {
        this.scheduleConfiguration = scheduleConfiguration;
    }

    public ResponseNotificationContent getResponseNotificationContent(String expected, String actual) {
        String title = "Got '" + actual + "', expected '" + expected + "'";
        String text = "Next one in " + timeUntilNextLearnificationText() + ".";
        return new ResponseNotificationContent(title, text);
    }

    private String timeUntilNextLearnificationText() {
        int delayInSeconds = scheduleConfiguration.getPeriodicityRange().earliestStartTimeDelayMs / 1000;

        if (delayInSeconds < 60) {
            return delayInSeconds + "s";
        } else {
            return (delayInSeconds / 60) + " mins";
        }
    }
}
