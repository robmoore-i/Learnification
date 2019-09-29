package com.rrm.learnification.settings;

import com.rrm.learnification.common.AndroidButton;
import com.rrm.learnification.common.AndroidLogger;

class SaveSettingsButton extends AndroidButton {
    SaveSettingsButton(AndroidLogger logger, SaveSettingsView saveSettingsView) {
        super(logger, saveSettingsView.saveSettingsButton());
    }

    @Override
    public boolean enabledInitially() {
        return true;
    }
}
