package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;

import java.util.HashMap;
import java.util.Map;

public class LearningItemUpdateBroker implements ItemUpdateBroker<LearningItem> {
    private final Map<LearningItem, ItemUpdateListener<LearningItem>> listeners = new HashMap<>();

    @Override
    public void sendUpdate(LearningItem target, LearningItem replacement) {
        ItemUpdateListener<LearningItem> itemUpdateListener = listeners.get(target);
        if (itemUpdateListener != null) {
            listeners.remove(target);
            itemUpdateListener.onItemChange(replacement);
        }
    }

    @Override
    public void put(LearningItem topic, ItemUpdateListener<LearningItem> subscriber) {
        listeners.put(topic, subscriber);
    }
}
