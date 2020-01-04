package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;

import java.util.HashMap;
import java.util.Map;

public class LearningItemChangeListenerGroup implements ItemChangeListenerGroup<LearningItem> {
    private final Map<LearningItem, ItemChangeListener<LearningItem>> listeners = new HashMap<>();

    @Override
    public void handleChange(LearningItem target, LearningItem replacement) {
        ItemChangeListener<LearningItem> itemChangeListener = listeners.get(target);
        if (itemChangeListener != null) {
            listeners.remove(target);
            itemChangeListener.onItemChange(replacement);
        }
    }

    @Override
    public void put(LearningItem topic, ItemChangeListener<LearningItem> subscriber) {
        listeners.put(topic, subscriber);
    }
}
