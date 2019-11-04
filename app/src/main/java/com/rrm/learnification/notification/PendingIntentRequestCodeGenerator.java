package com.rrm.learnification.notification;

import com.rrm.learnification.idgenerator.JavaInMemoryIdGenerator;

class PendingIntentRequestCodeGenerator {
    private static final PendingIntentRequestCodeGenerator instance = new PendingIntentRequestCodeGenerator();

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
