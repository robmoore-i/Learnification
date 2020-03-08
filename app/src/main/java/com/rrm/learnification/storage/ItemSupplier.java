package com.rrm.learnification.storage;

import java.util.List;

public interface ItemSupplier<T> {
    List<T> items();

    List<T> itemsOrThrowIfEmpty();
}
