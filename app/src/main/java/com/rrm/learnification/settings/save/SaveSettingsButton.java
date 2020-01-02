package com.rrm.learnification.settings.save;

import com.rrm.learnification.button.AndroidButton;
import com.rrm.learnification.logger.AndroidLogger;

public class SaveSettingsButton extends AndroidButton {
    public SaveSettingsButton(AndroidLogger logger, SaveSettingsView saveSettingsView) {
        super(logger, saveSettingsView.saveSettingsButton());
    }

    @Override
    public boolean enabledInitially() {
        return true;
    }
}
