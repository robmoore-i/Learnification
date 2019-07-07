package com.rrm.learnification;

class LearnificationResponseTextGenerator {
    private PeriodicityRange periodicityRangeForNextLearnification;

    LearnificationResponseTextGenerator(PeriodicityRange periodicityRangeForNextLearnification) {
        this.periodicityRangeForNextLearnification = periodicityRangeForNextLearnification;
    }

    String getReplyText(String inputString) {
        return "Got '" + inputString + "'. Next one in " + timeUntilNextLearnificationText() + ".";
    }

    private String timeUntilNextLearnificationText() {
        int delayInSeconds = periodicityRangeForNextLearnification.earliestStartTimeDelayMs / 1000;

        if (delayInSeconds < 60) {
            return delayInSeconds + "s";
        } else {
            return (delayInSeconds / 60) + " mins";
        }
    }
}
