package com.rrm.learnification.storage;

public interface ItemRepository<T> extends ItemSupplier<T> {
    void add(T item);

    void removeAt(int index);

    void remove(T item);

    void replace(T target, T replacement);

    void subscribeToModifications(T item, ItemUpdateListener<T> itemUpdateListener);
}
