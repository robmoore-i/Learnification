package com.rrm.learnification.learningitemseteditor.learningitemadd;

import com.rrm.learnification.button.OnClickCommand;

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
