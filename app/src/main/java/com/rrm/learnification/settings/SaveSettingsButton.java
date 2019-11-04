package com.rrm.learnification.settings;

import com.rrm.learnification.button.AndroidButton;
import com.rrm.learnification.logger.AndroidLogger;

class SaveSettingsButton extends AndroidButton {
    SaveSettingsButton(AndroidLogger logger, SaveSettingsView saveSettingsView) {
        super(logger, saveSettingsView.saveSettingsButton());
    }

    @Override
    public boolean enabledInitially() {
        return true;
    }
}
