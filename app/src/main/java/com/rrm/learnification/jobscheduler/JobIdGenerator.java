package com.rrm.learnification.jobscheduler;

import com.rrm.learnification.common.JavaInMemoryIdGenerator;

public class JobIdGenerator {
    private static final JobIdGenerator instance = new JobIdGenerator();

    private final JavaInMemoryIdGenerator javaInMemoryIdGenerator = new JavaInMemoryIdGenerator();

    private JobIdGenerator() {
    }

    public static JobIdGenerator getInstance() {
        return instance;
    }

    int nextJobId() {
        return javaInMemoryIdGenerator.nextId();
    }

    public int lastJobId() {
        return javaInMemoryIdGenerator.lastId();
    }
}