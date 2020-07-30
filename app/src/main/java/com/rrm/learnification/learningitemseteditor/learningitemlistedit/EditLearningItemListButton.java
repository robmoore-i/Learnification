package com.rrm.learnification.learningitemseteditor.learningitemlistedit;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.learningitemseteditor.learningitemadd.AddLearningItemButton;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.UpdateLearningItemButton;
import com.rrm.learnification.logger.AndroidLogger;

public class EditLearningItemListButton implements ConfigurableButton {
    private static final String LOG_TAG = "EditLearningItemListButton";

    private final AndroidLogger logger;
    private final AddLearningItemButton addLearningItemButton;
    private final UpdateLearningItemButton updateLearningItemButton;

    private ConfigurableButton activeButton;

    public EditLearningItemListButton(AndroidLogger logger, AddLearningItemButton addLearningItemButton, UpdateLearningItemButton updateLearningItemButton) {
        this.logger = logger;
        this.addLearningItemButton = addLearningItemButton;
        this.updateLearningItemButton = updateLearningItemButton;
    }

    public void takeRole(EditLearningItemListButtonRole role) {
        this.activeButton = selectActiveButton(role);
        logger.i(LOG_TAG, "set button role to be " + role.name());
        // TODO: Update the view to match the content of the activeButton.
    }

    private ConfigurableButton selectActiveButton(EditLearningItemListButtonRole role) {
        if (role == EditLearningItemListButtonRole.ADD) {
            return addLearningItemButton;
        } else {
            return updateLearningItemButton;
        }
    }

    @Override
    public void addOnClickHandler(OnClickCommand onClickCommand) {
        activeButton.addOnClickHandler(onClickCommand);
    }

    @Override
    public void addLastExecutedOnClickHandler(OnClickCommand onClickCommand) {
        activeButton.addLastExecutedOnClickHandler(onClickCommand);
    }

    @Override
    public void clearOnClickHandlers() {
        activeButton.clearOnClickHandlers();
    }

    @Override
    public void enable() {
        activeButton.enable();
    }

    @Override
    public void disable() {
        activeButton.disable();
    }

    @Override
    public void click() {
        activeButton.click();
    }
}
