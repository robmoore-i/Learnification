package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;

import java.util.HashMap;
import java.util.Map;

public class LearningItemUpdateBroker {
    private final Map<LearningItem, LearningItemUpdateListener> listeners = new HashMap<>();

    void sendUpdate(LearningItem target, LearningItem replacement) {
        LearningItemUpdateListener itemUpdateListener = listeners.get(target);
        if (itemUpdateListener != null) {
            listeners.remove(target);
            itemUpdateListener.onItemChange(replacement);
        }
    }

    void put(LearningItem topic, LearningItemUpdateListener subscriber) {
        listeners.put(topic, subscriber);
    }
}
