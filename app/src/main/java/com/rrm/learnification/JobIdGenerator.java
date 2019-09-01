package com.rrm.learnification;

class JobIdGenerator {
    private static JobIdGenerator instance = new JobIdGenerator();

    private final JavaInMemoryIdGenerator javaInMemoryIdGenerator = new JavaInMemoryIdGenerator();

    private JobIdGenerator() {
    }

    static JobIdGenerator getInstance() {
        return instance;
    }

    int nextJobId() {
        return javaInMemoryIdGenerator.nextId();
    }
}