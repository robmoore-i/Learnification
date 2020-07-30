package com.rrm.learnification.settings.save;

import com.rrm.learnification.button.ReifiedButton;
import com.rrm.learnification.logger.AndroidLogger;

public class SaveSettingsButton extends ReifiedButton {
    public SaveSettingsButton(AndroidLogger logger, SaveSettingsView saveSettingsView) {
        super(logger, saveSettingsView.saveSettingsButton(), "Save", true);
    }

}
