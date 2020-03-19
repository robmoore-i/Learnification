package com.rrm.learnification.common;

import android.support.annotation.NonNull;

import java.util.Objects;

public class LearningItemText {
    private final String left;
    private final String right;

    public LearningItemText(String left, String right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException();
        }
        this.left = left;
        this.right = right;
    }

    public static LearningItemText fromSingleString(String s) {
        String[] split = s.split("-");
        return new LearningItemText(split[0].trim(), split[1].trim());
    }

    public LearningItem withSet(String learningItemSetName) {
        return new LearningItem(left, right, learningItemSetName);
    }

    @NonNull
    @Override
    public String toString() {
        return left + " - " + right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningItemText that = (LearningItemText) o;
        return left.equals(that.left) &&
                right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
