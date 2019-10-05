package com.rrm.learnification.common;

import android.support.annotation.NonNull;

import java.util.Objects;

public class LearningItem {
    public final String left;
    public final String right;

    public LearningItem(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public String asSingleString() {
        return left + " - " + right;
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

    @NonNull
    @Override
    public String toString() {
        return "LearningItem{" +
                "left='" + left + '\'' +
                ", right='" + right + '\'' +
                '}';
    }
}
