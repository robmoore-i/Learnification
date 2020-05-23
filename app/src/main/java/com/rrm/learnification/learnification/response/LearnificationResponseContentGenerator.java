package com.rrm.learnification.learnification.response;

import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;

class LearnificationResponseContentGenerator {
    private final ScheduleConfiguration scheduleConfiguration;

    LearnificationResponseContentGenerator(ScheduleConfiguration scheduleConfiguration) {
        this.scheduleConfiguration = scheduleConfiguration;
    }

    NotificationTextContent getResponseNotificationTextContentForSubmittedText(LearnificationResponse learnificationResponse) {
        String expected = learnificationResponse.expectedUserResponse();
        String actual = learnificationResponse.actualUserResponse();
        String title = "Got '" + actual + "', expected '" + expected + "'";
        String text = "Next one in " + textForTimeUntilNextLearnification() + ".";
        return new NotificationTextContent(title, text);
    }

    NotificationTextContent getResponseNotificationTextContentForViewing(LearnificationResponse learnificationResponse) {
        String given = learnificationResponse.givenPrompt();
        String expected = learnificationResponse.expectedUserResponse();
        String title = given + " -> " + expected;
        String text = "Next one in " + textForTimeUntilNextLearnification() + ".";
        return new NotificationTextContent(title, text);
    }

    private String textForTimeUntilNextLearnification() {
        int delayInSeconds = scheduleConfiguration.getDelayRange().earliestStartTimeDelayMs / 1000;
        return delayInSeconds < 60 ? delayInSeconds + "s" : (delayInSeconds / 60) + " mins";
    }
}
