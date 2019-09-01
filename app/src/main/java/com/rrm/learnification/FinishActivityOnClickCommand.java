package com.rrm.learnification;

import android.app.Activity;

class FinishActivityOnClickCommand implements OnClickCommand {
    private final Activity activity;

    FinishActivityOnClickCommand(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick() {
        activity.finish();
    }
}
