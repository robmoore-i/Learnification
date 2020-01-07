package com.rrm.learnification.idgenerator;

public class JavaInMemoryIdGenerator implements IdGenerator {
    private int nextId = 0;
    private int lastId = 0;

    @Override
    public int nextId() {
        lastId = nextId;
        return nextId++;
    }

    @Override
    public int lastId() {
        return lastId;
    }
}