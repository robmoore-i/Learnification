package com.rrm.learnification.button;

import com.rrm.learnification.textinput.TextInput;

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
