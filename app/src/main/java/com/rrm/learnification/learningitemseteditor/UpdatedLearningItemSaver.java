package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.ItemUpdateListener;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.textlist.ListViewItemSaver;
import com.rrm.learnification.textlist.TextSource;

import java.util.List;
import java.util.function.BiConsumer;

public class UpdatedLearningItemSaver implements ListViewItemSaver, ItemUpdateListener<LearningItem> {
    private static final String LOG_TAG = "UpdatedLearningItemSaver";

    private final AndroidLogger logger;
    private final PersistentLearningItemRepository learningItemRepository;

    private TextSource textSource;
    private String savedText;

    UpdatedLearningItemSaver(AndroidLogger logger, PersistentLearningItemRepository learningItemRepository) {
        this.logger = logger;
        this.learningItemRepository = learningItemRepository;
    }

    @Override
    public void saveText(TextSource textSource, String savedText) {
        logger.v(LOG_TAG, "saving text '" + savedText + "' from text source with current value '" + textSource.get() + "'");
        this.textSource = textSource;
        this.savedText = savedText;
        learningItemRepository.subscribeToModifications(savedLearningItem(), this);
    }

    @Override
    public String savedText() {
        List<LearningItem> items = learningItemRepository.items();
        String s;
        if (!items.contains(savedLearningItem())) {
            s = textSource.get();
        } else {
            s = savedText;
        }
        logger.v(LOG_TAG, "returning saved text '" + s + "'");
        return s;

    }

    @Override
    public void onItemChange(LearningItem updatedItem) {
        String updatedText = updatedItem.toDisplayString();
        logger.v(LOG_TAG, "item with saved text '" + savedText + "' updated to '" + updatedText + "'");
        savedText = updatedText;
        learningItemRepository.subscribeToModifications(savedLearningItem(), this);
    }

    void updateUsing(BiConsumer<LearningItem, LearningItem> learningItemConsumer) {
        if (!textSource.isEmpty()) {
            LearningItem target = savedLearningItem();
            LearningItem replacement = liveLearningItem();
            logger.v(LOG_TAG, "updating learning item from '" + target + "' to '" + replacement + "'");
            learningItemConsumer.accept(target, replacement);
        }
    }

    private LearningItem liveLearningItem() {
        return LearningItem.fromSingleString(textSource.get());
    }

    private LearningItem savedLearningItem() {
        return LearningItem.fromSingleString(savedText);
    }
}
