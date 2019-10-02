package com.rrm.learnification.common;

public class LearningItem {
    final String left;
    final String right;

    public LearningItem(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public static LearningItem fromLine(String line) {
        if ("".equals(line)) {
            return new EmptyLearningItem();
        }
        String[] split = line.split(" - ");
        if (split.length != 2) {
            return new EmptyLearningItem();
        }
        return new LearningItem(split[0], split[1]);
    }

    public String asSingleString() {
        return left + " - " + right;
    }

    public boolean isEmpty() {
        return false;
    }

    private static class EmptyLearningItem extends LearningItem {
        EmptyLearningItem() {
            super("/", "/");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }
}
