package com.rrm.learnification;

class LearningItemTemplate {
    private final String leftPrompt;
    private final String rightPrompt;

    LearningItemTemplate(String leftPrompt, String rightPrompt) {
        this.leftPrompt = leftPrompt;
        this.rightPrompt = rightPrompt;
    }

    LearningItem build(String left, String right) {
        return new LearningItem(leftPrompt + " " + left + "?", rightPrompt + " " + right + "?");
    }
}
