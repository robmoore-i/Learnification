package com.rrm.learnification.response;

import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;

class LearnificationResponseContentGenerator {
    private final ScheduleConfiguration scheduleConfiguration;

    LearnificationResponseContentGenerator(ScheduleConfiguration scheduleConfiguration) {
        this.scheduleConfiguration = scheduleConfiguration;
    }

    NotificationTextContent getResponseNotificationTextContent(String expected, String actual) {
        String title = "Got '" + actual + "', expected '" + expected + "'";
        String text = "Next one in " + timeUntilNextLearnificationText() + ".";
        return new NotificationTextContent(title, text);
    }

    private String timeUntilNextLearnificationText() {
        int delayInSeconds = scheduleConfiguration.getDelayRange().earliestStartTimeDelayMs / 1000;

        if (delayInSeconds < 60) {
            return delayInSeconds + "s";
        } else {
            return (delayInSeconds / 60) + " mins";
        }
    }
}
