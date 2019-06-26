package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

class LearnificationButton {
    private static final String LOG_TAG = "LearnificationButton";
    private final MainActivity activity;
    private final AndroidLogger androidLogger;

    LearnificationButton(MainActivity activity, AndroidLogger androidLogger) {
        this.activity = activity;
        this.androidLogger = androidLogger;
    }

    void configure() {
        FloatingActionButton button = activity.findViewById(R.id.addLearnificationButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidLogger.v(LOG_TAG, "addLearnificationButton clicked");
            }
        });
    }
}
