package com.rrm.learnification.storage;

/**
 * Implementations of this interface provide a way for objects to listen for updates to individual
 * items in a collection of objects with type T.
 *
 * @param <T> The type of item being listened for
 */
public interface ItemUpdateBroker<T> {
    void sendUpdate(T target, T replacement);

    void put(T topic, ItemUpdateListener<T> subscriber);
}
