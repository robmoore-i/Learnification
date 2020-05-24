package com.rrm.learnification.learnificationresultstorage;

import java.time.LocalDateTime;
import java.util.Objects;

public class LearnificationResult {
    final LocalDateTime timeSubmitted;
    final LearnificationResultEvaluation result;
    final String given;
    final String expected;

    public LearnificationResult(LocalDateTime timeSubmitted, LearnificationResultEvaluation result, LearnificationPrompt learnificationPrompt) {
        this.timeSubmitted = timeSubmitted;
        this.result = result;
        this.given = learnificationPrompt.given;
        this.expected = learnificationPrompt.expected;
    }

    @Override
    public String toString() {
        return "LearnificationResult{" +
                "timeSubmitted=" + timeSubmitted +
                ", result=" + result +
                ", given='" + given + '\'' +
                ", expected='" + expected + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearnificationResult that = (LearnificationResult) o;
        return timeSubmitted.equals(that.timeSubmitted) &&
                result == that.result &&
                given.equals(that.given) &&
                expected.equals(that.expected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeSubmitted, result, given, expected);
    }
}
