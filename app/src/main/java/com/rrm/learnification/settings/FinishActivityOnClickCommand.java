package com.rrm.learnification.settings;

import android.app.Activity;

import com.rrm.learnification.button.OnClickCommand;

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
