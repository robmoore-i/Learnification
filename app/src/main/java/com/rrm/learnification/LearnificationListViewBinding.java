package com.rrm.learnification;

import android.widget.ArrayAdapter;

class LearnificationListViewBinding {
    private final LearnificationRepository learnificationRepository;
    private final ArrayAdapter<String> listViewAdapter;

    LearnificationListViewBinding(LearnificationRepository learnificationRepository, ArrayAdapter<String> learnificationListViewAdapter) {
        this.learnificationRepository = learnificationRepository;
        this.listViewAdapter = learnificationListViewAdapter;
    }

    void addLearnificationToListView(LearningItem learningItem) {
        learnificationRepository.add(learningItem);
        listViewAdapter.add(learningItem.asSingleString());
    }
}
