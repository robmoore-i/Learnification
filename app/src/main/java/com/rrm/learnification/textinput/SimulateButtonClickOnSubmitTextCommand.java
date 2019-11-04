package com.rrm.learnification.textinput;

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
