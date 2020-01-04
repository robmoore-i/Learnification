package com.rrm.learnification.storage;

public interface ItemChangeListenerGroup<T> {
    void handleChange(T target, T replacement);

    void put(T topic, ItemChangeListener<T> subscriber);
}
