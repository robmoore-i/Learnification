package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;

class AndroidLearnificationButtonContext {
    private MainActivity activity;

    AndroidLearnificationButtonContext(MainActivity activity) {
        this.activity = activity;
    }

    FloatingActionButton getLearnificationButton() {
        return activity.findViewById(R.id.addLearnificationButton);
    }
}
