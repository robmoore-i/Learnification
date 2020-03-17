package com.rrm.learnification.common;

import android.support.annotation.NonNull;

import java.util.Objects;

public class LearningItem {
    public final String left;
    public final String right;

    // TODO: Add learningItemSetName as third parameter here. Deal with the consequences. Reap the rewards.
    public LearningItem(String left, String right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException();
        }
        this.left = left;
        this.right = right;
    }

    public static LearningItem fromSingleString(String s) {
        String[] split = s.split("-");
        return new LearningItem(split[0].trim(), split[1].trim());
    }

    public String toDisplayString() {
        return left + " - " + right;
    }

    @NonNull
    @Override
    public String toString() {
        return "LearningItem{" +
                "left='" + left + '\'' +
                ", right='" + right + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningItem that = (LearningItem) o;
        return left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
