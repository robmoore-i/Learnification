package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.LearningItemUpdateListener;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.textlist.LearningItemDisplayStash;
import com.rrm.learnification.textlist.TextSource;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class UpdatableLearningItemDisplayStash implements LearningItemDisplayStash, LearningItemUpdateListener {
    private static final String LOG_TAG = "UpdatedLearningItemSaver";

    private final AndroidLogger logger;
    private final PersistentLearningItemRepository learningItemRepository;

    private TextSource textSource;
    private LearningItem savedLearningItem;

    UpdatableLearningItemDisplayStash(AndroidLogger logger, PersistentLearningItemRepository learningItemRepository) {
        this.logger = logger;
        this.learningItemRepository = learningItemRepository;
    }

    @Override
    public void saveText(TextSource textSource, String savedText) {
        logger.v(LOG_TAG, "saving text '" + savedText + "' from text source with current value '" + textSource.get() + "'");
        this.textSource = textSource;
        saveLearningItem(learningItemRepository.get(learningItem -> savedText.equals(learningItem.toDisplayString())));
    }

    @Override
    public String savedText() {
        logger.v(LOG_TAG, "returning saved text '" + savedLearningItem.toDisplayString() + "'");
        return savedLearningItem.toDisplayString();

    }

    @Override
    public void onItemChange(LearningItem updatedItem) {
        saveLearningItem(updatedItem);
    }

    /**
     * Overwrites the stashed value using the live, updatable value. It does this using the given
     * BiConsumer.
     */
    void commit(BiConsumer<LearningItem, Function<String, LearningItem>> learningItemConsumer) {
        if (!textSource.isEmpty()) {
            LearningItem target = savedLearningItem();
            Function<String, LearningItem> replacement = liveLearningItem();
            learningItemConsumer.accept(target, replacement);
            logger.v(LOG_TAG, "committed learning item from '" + target.toDisplayString() + "' to '" + liveText() + "'");
        }
    }

    private String liveText() {
        return textSource.get();
    }

    private Function<String, LearningItem> liveLearningItem() {
        return LearningItem.fromSingleString(liveText());
    }

    private LearningItem savedLearningItem() {
        return savedLearningItem;
    }

    private void saveLearningItem(LearningItem learningItem) {
        this.savedLearningItem = learningItem;
        learningItemRepository.subscribeToModifications(learningItem, this);
        logger.v(LOG_TAG, "updated a learning item to '" + learningItem.toString() + "'");
    }
}
