package com.rrm.learnification.storage;

import java.util.List;

public interface ItemRepository<T> {
    List<T> items();

    List<T> itemsOrThrowIfEmpty();

    void add(T item);

    void removeAt(int index);

    void remove(T item);

    void replace(T target, T replacement);

    void subscribeToModifications(T item, ItemChangeListener<T> itemChangeListener);
}
