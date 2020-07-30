package com.rrm.learnification.settings.save;

import android.app.Activity;

import com.rrm.learnification.button.OnClickCommand;

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
