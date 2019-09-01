package com.rrm.learnification;

class JavaInMemoryIdGenerator {
    private int nextId = 0;
    private int lastId = 0;

    int nextId() {
        lastId = nextId;
        return nextId++;
    }

    void reset() {
        nextId = 0;
    }

    int lastId() {
        return lastId;
    }
}