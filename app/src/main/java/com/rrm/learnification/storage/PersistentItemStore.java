package com.rrm.learnification.storage;

import java.util.List;

public interface PersistentItemStore<T> {
    List<T> read();

    void write(T learningItem);

    void remove(T learningItem);

    void rewrite(List<T> learningItems);

    void replace(T target, T replacement);
}
