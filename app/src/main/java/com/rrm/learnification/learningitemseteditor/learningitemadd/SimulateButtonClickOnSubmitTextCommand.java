package com.rrm.learnification.learningitemseteditor.learningitemadd;

import com.rrm.learnification.button.ConfigurableButton;

public class SimulateButtonClickOnSubmitTextCommand implements OnSubmitTextCommand {
    private final ConfigurableButton button;

    public SimulateButtonClickOnSubmitTextCommand(ConfigurableButton button) {
        this.button = button;
    }

    @Override
    public void onSubmit() {
        button.click();
    }
}
