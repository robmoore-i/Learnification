package com.rrm.learnification.learningitemseteditor.learnificationtoolbar.viewparameters;

import com.rrm.learnification.button.ConfigurableButton;

class LearnificationReady extends EquatableToolbarViewParameters {
    @Override
    public String toolbarTitle() {
        return "Learnification ready";
    }

    @Override
    public String getName() {
        return "ready";
    }

    @Override
    public void configureFastForwardScheduleButton(ConfigurableButton fastForwardScheduleButton) {
        fastForwardScheduleButton.disable();
    }
}
