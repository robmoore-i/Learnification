package com.rrm.learnification;

class PendingIntentRequestCodeGenerator {
    private static PendingIntentRequestCodeGenerator instance = new PendingIntentRequestCodeGenerator();

    private final JavaInMemoryIdGenerator javaInMemoryIdGenerator = new JavaInMemoryIdGenerator();

    private PendingIntentRequestCodeGenerator() {
    }

    static PendingIntentRequestCodeGenerator getInstance() {
        return instance;
    }

    int nextRequestCode() {
        return javaInMemoryIdGenerator.nextId();
    }
}
