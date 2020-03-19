package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItemText;

import java.util.HashMap;
import java.util.Map;

public class LearningItemTextUpdateBroker {
    private final Map<LearningItemText, LearningItemTextUpdateListener> listeners = new HashMap<>();

    void sendUpdate(LearningItemText target, LearningItemText replacement) {
        LearningItemTextUpdateListener itemUpdateListener = listeners.get(target);
        if (itemUpdateListener != null) {
            listeners.remove(target);
            itemUpdateListener.onItemChange(replacement);
        }
    }

    void put(LearningItemText topic, LearningItemTextUpdateListener subscriber) {
        listeners.put(topic, subscriber);
    }
}
