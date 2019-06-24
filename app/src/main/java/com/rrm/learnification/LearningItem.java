package com.rrm.learnification;

class LearningItem {
    final String left;
    final String right;

    LearningItem(String left, String right) {
        this.left = left;
        this.right = right;
    }

    String asSingleString() {
        return left + " - " + right;
    }
}
