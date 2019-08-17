package com.rrm.learnification;

class LearningItem {
    final String left;
    final String right;

    LearningItem(String left, String right) {
        this.left = left;
        this.right = right;
    }

    static LearningItem fromLine(String line) {
        if ("".equals(line)) {
            return new EmptyLearningItem();
        }
        String[] split = line.split(" - ");
        if (split.length != 2) {
            return new EmptyLearningItem();
        }
        return new LearningItem(split[0], split[1]);
    }

    String asSingleString() {
        return left + " - " + right;
    }

    boolean isEmpty() {
        return false;
    }

    private static class EmptyLearningItem extends LearningItem {
        EmptyLearningItem() {
            super("/", "/");
        }

        @Override
        boolean isEmpty() {
            return true;
        }
    }
}
