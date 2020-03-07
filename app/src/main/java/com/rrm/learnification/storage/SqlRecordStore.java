package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;

import java.util.List;

interface SqlRecordStore<T> {
    List<T> readAll();

    void write(T item);

    void writeAll(List<T> items);

    void deleteAll();

    void delete(T item);

    void replace(LearningItem target, LearningItem replacement);
}
