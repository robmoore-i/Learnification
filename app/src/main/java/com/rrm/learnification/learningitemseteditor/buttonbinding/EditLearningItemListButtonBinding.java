package com.rrm.learnification.learningitemseteditor.buttonbinding;

import com.rrm.learnification.button.LogicalButton;
import com.rrm.learnification.learningitemseteditor.learningitemadd.AddLearningItemButton;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.UpdateLearningItemButton;
import com.rrm.learnification.logger.AndroidLogger;

public class EditLearningItemListButtonBinding {
    private static final String LOG_TAG = "EditLearningItemListButtonBinding";

    private final AndroidLogger logger;
    private final AddLearningItemButton addLearningItemButton;
    private final UpdateLearningItemButton updateLearningItemButton;

    private LogicalButton activeButton;

    public EditLearningItemListButtonBinding(AndroidLogger logger, AddLearningItemButton addLearningItemButton,
                                             UpdateLearningItemButton updateLearningItemButton) {
        this.logger = logger;
        this.addLearningItemButton = addLearningItemButton;
        this.updateLearningItemButton = updateLearningItemButton;

        this.activeButton = addLearningItemButton;
    }

    public void bindToRole(EditLearningItemListButtonRole role) {
        activeButton.detach();
        activeButton = selectActiveButton(role);
        activeButton.attach();
        logger.i(LOG_TAG, "set button role to be " + role.name());
    }

    private LogicalButton selectActiveButton(EditLearningItemListButtonRole role) {
        if (role == EditLearningItemListButtonRole.ADD) {
            return addLearningItemButton;
        } else {
            return updateLearningItemButton;
        }
    }
}
