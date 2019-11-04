package com.rrm.learnification.main;

import com.rrm.learnification.textinput.OnSubmitTextCommand;

class AddLearningItemOnSubmitCommand implements OnSubmitTextCommand {
    private final AddLearningItemButton addLearningItemButton;

    AddLearningItemOnSubmitCommand(AddLearningItemButton addLearningItemButton) {
        this.addLearningItemButton = addLearningItemButton;
    }

    @Override
    public void onSubmit() {
        addLearningItemButton.click();
    }
}
