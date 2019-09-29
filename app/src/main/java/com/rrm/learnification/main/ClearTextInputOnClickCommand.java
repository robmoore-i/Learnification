package com.rrm.learnification.main;

import com.rrm.learnification.common.OnClickCommand;

class ClearTextInputOnClickCommand implements OnClickCommand {
    private final LearningItemTextInput learningItemTextInput;

    ClearTextInputOnClickCommand(LearningItemTextInput learningItemTextInput) {
        this.learningItemTextInput = learningItemTextInput;
    }

    @Override
    public void onClick() {
        learningItemTextInput.clear();
    }
}
