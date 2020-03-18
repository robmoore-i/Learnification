package com.rrm.learnification.common;

import android.support.annotation.NonNull;

import java.util.Objects;
import java.util.function.Function;

public class LearningItem {
    public final String left;
    public final String right;
    private final String learningItemSetName;

    // TODO: Add learningItemSetName as third parameter here. Deal with the consequences. Reap the rewards.
    public LearningItem(String left, String right) {
        this(left, right, "default");
    }

    public LearningItem(String left, String right, String learningItemSetName) {
        if (left == null || right == null || learningItemSetName == null) {
            throw new IllegalArgumentException();
        }
        this.left = left;
        this.right = right;
        this.learningItemSetName = learningItemSetName;
    }

    public static Function<String, LearningItem> fromSingleString(String s) {
        String[] split = s.split("-");
        return learningItemSetName -> new LearningItem(split[0].trim(), split[1].trim(), learningItemSetName);
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
                ", learningItemSetName='" + learningItemSetName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningItem that = (LearningItem) o;
        return left.equals(that.left) &&
                right.equals(that.right) &&
                learningItemSetName.equals(that.learningItemSetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, learningItemSetName);
    }
}
