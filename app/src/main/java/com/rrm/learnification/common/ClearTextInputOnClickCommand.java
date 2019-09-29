package com.rrm.learnification.common;

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
