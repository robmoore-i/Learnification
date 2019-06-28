package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;

class LearnificationButton {
    private static final String LOG_TAG = "LearnificationButton";
    private final MainActivity activity;
    private final AndroidLogger androidLogger;

    LearnificationButton(MainActivity activity, AndroidLogger androidLogger) {
        this.activity = activity;
        this.androidLogger = androidLogger;
    }

    void configure(final LearnificationRepository learnificationRepository, final ArrayAdapter<String> listViewAdapter) {
        FloatingActionButton button = activity.findViewById(R.id.addLearnificationButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidLogger.v(LOG_TAG, "addLearnificationButton clicked");
                LearningItem learningItem = learnificationRepository.addLearningItem();
                listViewAdapter.add(learningItem.asSingleString());
            }
        });
    }
}
