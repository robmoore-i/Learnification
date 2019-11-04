package com.rrm.learnification.button;

import android.app.Activity;

public class FinishActivityOnClickCommand implements OnClickCommand {
    private final Activity activity;

    public FinishActivityOnClickCommand(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick() {
        activity.finish();
    }
}
