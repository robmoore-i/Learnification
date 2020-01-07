package com.rrm.learnification.idgenerator;

public interface IdGenerator {
    int nextId();

    int lastId();

    void reset();
}
