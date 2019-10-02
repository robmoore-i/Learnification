package com.rrm.learnification.learnification;

import com.rrm.learnification.common.JavaInMemoryIdGenerator;

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