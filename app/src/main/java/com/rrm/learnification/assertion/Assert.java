package com.rrm.learnification.assertion;

public class Assert {
    public static void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
