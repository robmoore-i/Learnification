package com.rrm.learnification.main;

import com.rrm.learnification.textinput.OnSubmitTextAction;

class AddLearningItemOnSubmitAction implements OnSubmitTextAction {
    private final AddLearningItemButton addLearningItemButton;

    AddLearningItemOnSubmitAction(AddLearningItemButton addLearningItemButton) {
        this.addLearningItemButton = addLearningItemButton;
    }

    @Override
    public void onSubmit() {
        addLearningItemButton.click();
    }
}
