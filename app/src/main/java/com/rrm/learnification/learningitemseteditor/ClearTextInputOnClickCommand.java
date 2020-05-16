package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.button.OnClickCommand;

class ClearTextInputOnClickCommand implements OnClickCommand {
    private final TextInput textInput;

    ClearTextInputOnClickCommand(TextInput textInput) {
        this.textInput = textInput;
    }

    @Override
    public void onClick() {
        textInput.clear();
    }
}
