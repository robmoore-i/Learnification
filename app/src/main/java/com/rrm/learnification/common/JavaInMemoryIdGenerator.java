package com.rrm.learnification.common;

public class JavaInMemoryIdGenerator {
    private int nextId = 0;
    private int lastId = 0;

    public int nextId() {
        lastId = nextId;
        return nextId++;
    }

    public int lastId() {
        return lastId;
    }

    void reset() {
        nextId = 0;
    }
}