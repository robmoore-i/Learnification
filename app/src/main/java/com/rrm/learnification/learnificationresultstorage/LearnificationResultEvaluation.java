package com.rrm.learnification.learnificationresultstorage;

public enum LearnificationResultEvaluation {
    CORRECT,
    INCORRECT;

    public static LearnificationResultEvaluation parse(String string) {
        for (LearnificationResultEvaluation value : values()) {
            if (string.equals(value.name())) return value;
        }
        throw new IllegalStateException("A LearnificationResultBinary '" + string + "' could not be parsed");
    }
}
