package com.rrm.learnification.jobs;

import com.rrm.learnification.idgenerator.JavaInMemoryIdGenerator;

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