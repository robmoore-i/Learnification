package com.rrm.learnification;

class LearnificationResponseTextGenerator {
    private final ScheduleConfiguration scheduleConfiguration;

    LearnificationResponseTextGenerator(ScheduleConfiguration scheduleConfiguration) {
        this.scheduleConfiguration = scheduleConfiguration;
    }

    String getReplyText(String inputString) {
        return "Got '" + inputString + "'. Next one in " + timeUntilNextLearnificationText() + ".";
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
