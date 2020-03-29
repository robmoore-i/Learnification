package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.LearningItemTextUpdateListener;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.textlist.LearningItemDisplayStash;
import com.rrm.learnification.textlist.TextSource;

import java.util.function.BiConsumer;

public class UpdatableLearningItemTextDisplayStash implements LearningItemDisplayStash, LearningItemTextUpdateListener {
    private static final String LOG_TAG = "UpdatableLearningItemTextDisplayStash";

    private final AndroidLogger logger;
    private final PersistentLearningItemRepository learningItemRepository;

    private TextSource textSource;
    private LearningItemText savedLearningItemText;

    UpdatableLearningItemTextDisplayStash(AndroidLogger logger, PersistentLearningItemRepository learningItemRepository) {
        this.logger = logger;
        this.learningItemRepository = learningItemRepository;
    }

    @Override
    public void saveText(TextSource textSource, String savedText) {
        logger.i(LOG_TAG, "saving text '" + savedText + "' from text source with current value '" + textSource.get() + "'");
        this.textSource = textSource;
        saveLearningItem(LearningItemText.fromSingleString(savedText));
    }

    @Override
    public String savedText() {
        logger.i(LOG_TAG, "returning saved text '" + savedLearningItemText + "'");
        return savedLearningItemText.toString();

    }

    @Override
    public void onItemChange(LearningItemText updatedItemText) {
        saveLearningItem(updatedItemText);
    }

    /**
     * Overwrites the stashed value using the live, updatable value. It does this using the given
     * BiConsumer.
     */
    void commit(BiConsumer<LearningItemText, LearningItemText> learningItemTextConsumer) {
        if (!textSource.isEmpty()) {
            LearningItemText target = savedLearningItemText;
            LearningItemText replacement = liveLearningItemText();
            learningItemTextConsumer.accept(target, replacement);
            logger.u(LOG_TAG, "updated learning item from '" + target.toString() + "' to '" + replacement.toString() + "'");
        }
    }

    private LearningItemText liveLearningItemText() {
        return LearningItemText.fromSingleString(textSource.get());
    }

    private void saveLearningItem(LearningItemText learningItemText) {
        this.savedLearningItemText = learningItemText;
        learningItemRepository.subscribeToModifications(learningItemText, this);
        logger.i(LOG_TAG, "updated a learning item to '" + learningItemText + "'");
    }
}
