package com.rrm.learnification.learningitemseteditor.buttonbinding;

public class SetButtonIntoAddModeOnFocusGained implements OnFocusGainedCommand {
    private final EditLearningItemListButtonBinding buttonBinding;

    public SetButtonIntoAddModeOnFocusGained(EditLearningItemListButtonBinding buttonBinding) {
        this.buttonBinding = buttonBinding;
    }

    @Override
    public void focusGained() {
        buttonBinding.bindToRole(EditLearningItemListButtonRole.ADD);
    }
}
