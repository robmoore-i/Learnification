package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.TextInput;

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
