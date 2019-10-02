package com.rrm.learnification.storage;

import java.util.List;

public interface ItemRepository<T> {
    List<T> items();

    void add(T learningItem);

    void removeAt(int index);
}
