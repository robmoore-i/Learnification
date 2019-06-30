package com.rrm.learnification;

class LearningItem {
    final String left;
    final String right;

    LearningItem(String left, String right) {
        this.left = left;
        this.right = right;
    }

    static LearningItem fromLine(String line) {
        String[] split = line.split(" - ");
        return new LearningItem(split[0], split[1]);
    }

    String asSingleString() {
        return left + " - " + right;
    }
}
