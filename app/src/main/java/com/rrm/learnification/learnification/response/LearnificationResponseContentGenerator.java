package com.rrm.learnification.learnification.response;

import com.rrm.learnification.learnification.response.learnificationresponse.LearnificationResponse;
import com.rrm.learnification.notification.NotificationTextContent;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;

public class LearnificationResponseContentGenerator {
    private final ScheduleConfiguration scheduleConfiguration;

    LearnificationResponseContentGenerator(ScheduleConfiguration scheduleConfiguration) {
        this.scheduleConfiguration = scheduleConfiguration;
    }

    public NotificationTextContent getResponseNotificationTextContentForSubmittedText(LearnificationResponse learnificationResponse) {
        String expected = learnificationResponse.expectedUserResponse();
        String actual = learnificationResponse.actualUserResponse();
        String title = "Got '" + actual + "', expected '" + expected + "'";
        String text = "Next one in " + textForTimeUntilNextLearnification() + ".";
        return new NotificationTextContent(title, text);
    }

    public NotificationTextContent getResponseNotificationTextContentForViewing(LearnificationResponse learnificationResponse) {
        String given = learnificationResponse.givenPrompt();
        String expected = learnificationResponse.expectedUserResponse();
        String title = given + " -> " + expected;
        String text = "Next one in " + textForTimeUntilNextLearnification() + ".";
        return new NotificationTextContent(title, text);
    }

    private String textForTimeUntilNextLearnification() {
        int delayInSeconds = scheduleConfiguration.getConfiguredDelayRange().earliestStartTimeDelayMs / 1000;
        return delayInSeconds < 60 ? delayInSeconds + "s" : (delayInSeconds / 60) + " mins";
    }
}
