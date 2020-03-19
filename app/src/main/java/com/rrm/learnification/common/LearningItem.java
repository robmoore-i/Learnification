package com.rrm.learnification.common;

import android.support.annotation.NonNull;

import java.util.Objects;

public class LearningItem {
    public final String left;
    public final String right;
    public final String learningItemSetName;

    public LearningItem(String left, String right, String learningItemSetName) {
        if (left == null || right == null || learningItemSetName == null) {
            throw new IllegalArgumentException();
        }
        this.left = left;
        this.right = right;
        this.learningItemSetName = learningItemSetName;
    }

    public LearningItemText toDisplayString() {
        return new LearningItemText(left, right);
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
