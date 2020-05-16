package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.button.ConfigurableButton;

class SimulateButtonClickOnSubmitTextCommand implements OnSubmitTextCommand {
    private final ConfigurableButton button;

    SimulateButtonClickOnSubmitTextCommand(ConfigurableButton button) {
        this.button = button;
    }

    @Override
    public void onSubmit() {
        button.click();
    }
}
