package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;

class LearnificationButton {
    private static final String LOG_TAG = "LearnificationButton";
    private final AndroidLogger androidLogger;
    private AndroidLearnificationButtonContext context;

    LearnificationButton(AndroidLogger androidLogger, AndroidLearnificationButtonContext androidLearnificationButtonContext) {
        this.androidLogger = androidLogger;
        this.context = androidLearnificationButtonContext;
    }

    void configure(final LearnificationRepository learnificationRepository, final ArrayAdapter<String> listViewAdapter) {
        FloatingActionButton button = context.getLearnificationButton();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidLogger.v(LOG_TAG, "addLearnificationButton clicked");
                listViewAdapter.add(learnificationRepository.addLearningItem().asSingleString());
            }
        });
    }

}
