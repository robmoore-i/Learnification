package com.rrm.learnification.common;

public class ClearTextInputOnClickCommand implements OnClickCommand {
    private final TextInput textInput;

    public ClearTextInputOnClickCommand(TextInput textInput) {
        this.textInput = textInput;
    }

    @Override
    public void onClick() {
        textInput.clear();
    }
}
