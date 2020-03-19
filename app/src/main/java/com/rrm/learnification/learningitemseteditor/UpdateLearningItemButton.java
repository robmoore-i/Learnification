package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.button.AndroidButton;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.function.BiConsumer;

class UpdateLearningItemButton extends AndroidButton {
    private final UpdatableLearningItemTextDisplayStash updatableLearningItemDisplayStash;

    UpdateLearningItemButton(AndroidLogger logger, UpdateLearningItemView view, UpdatableLearningItemTextDisplayStash updatableLearningItemDisplayStash) {
        super(logger, view.updateLearningItemButton(), false);
        this.updatableLearningItemDisplayStash = updatableLearningItemDisplayStash;
    }

    /**
     * @param learningItemConsumer This is a function accepting two arguments and returning no result.
     *                             The first argument is the LearningItem object representing the initial state (before updates)
     *                             The second argument is the LearningItem object representing the final state (after updates)
     */
    void addOnClickHandler(BiConsumer<LearningItemText, LearningItemText> learningItemConsumer) {
        addOnClickHandler(() -> updatableLearningItemDisplayStash.commit(learningItemConsumer));
    }
}
