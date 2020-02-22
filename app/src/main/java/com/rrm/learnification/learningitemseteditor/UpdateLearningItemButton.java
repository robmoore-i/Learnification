package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.button.AndroidButton;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.function.BiConsumer;

class UpdateLearningItemButton extends AndroidButton {
    private final UpdatedLearningItemSaver updatedLearningItemSaver;

    UpdateLearningItemButton(AndroidLogger logger, UpdateLearningItemView view, UpdatedLearningItemSaver updatedLearningItemSaver) {
        super(logger, view.updateLearningItemButton());
        this.updatedLearningItemSaver = updatedLearningItemSaver;
    }

    @Override
    public boolean enabledInitially() {
        return false;
    }

    /**
     * @param learningItemConsumer This is a function accepting two arguments and returning no result.
     *                             The first argument is the LearningItem object representing the initial state (before updates)
     *                             The second argument is the LearningItem object representing the final state (after updates)
     */
    void addOnClickHandler(BiConsumer<LearningItem, LearningItem> learningItemConsumer) {
        addOnClickHandler(() -> updatedLearningItemSaver.updateUsing(learningItemConsumer));
    }
}
