package com.rrm.learnification;

class SaveSettingsButton extends AndroidButton {
    SaveSettingsButton(AndroidLogger logger, SaveSettingsView saveSettingsView) {
        super(logger, saveSettingsView.saveSettingsButton());
    }

    @Override
    boolean enabledInitially() {
        return true;
    }
}
