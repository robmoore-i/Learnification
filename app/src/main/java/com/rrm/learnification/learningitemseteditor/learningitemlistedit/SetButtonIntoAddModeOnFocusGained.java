package com.rrm.learnification.learningitemseteditor.learningitemlistedit;

public class SetButtonIntoAddModeOnFocusGained implements OnFocusGainedCommand {
    private final EditLearningItemListButton editLearningItemListButton;

    public SetButtonIntoAddModeOnFocusGained(EditLearningItemListButton editLearningItemListButton) {
        this.editLearningItemListButton = editLearningItemListButton;
    }

    @Override
    public void focusGained() {
        editLearningItemListButton.takeRole(EditLearningItemListButtonRole.ADD);
    }
}
