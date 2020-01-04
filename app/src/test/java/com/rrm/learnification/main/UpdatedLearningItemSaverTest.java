package com.rrm.learnification.main;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.ItemRepository;
import com.rrm.learnification.textlist.TextSource;

import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdatedLearningItemSaverTest {
    @SuppressWarnings("unchecked")
    private final ItemRepository<LearningItem> stubLearningItemRepository = mock(ItemRepository.class);
    private final AndroidLogger dummyLogger = mock(AndroidLogger.class);

    @Test
    public void revertsUnpersistedChanges() {
        UpdatedLearningItemSaver updatedLearningItemSaver = new UpdatedLearningItemSaver(dummyLogger, stubLearningItemRepository);
        String initialEntry = "a - b";
        String updatedEntry = "a - c";
        when(stubLearningItemRepository.items()).thenReturn(Collections.singletonList(LearningItem.fromSingleString(initialEntry)));

        updatedLearningItemSaver.saveText(new TextSource.StableTextSource(updatedEntry), initialEntry);

        assertThat(updatedLearningItemSaver.savedText(), equalTo(initialEntry));
    }

    @Test
    public void savesPersistedChanges() {
        UpdatedLearningItemSaver updatedLearningItemSaver = new UpdatedLearningItemSaver(dummyLogger, stubLearningItemRepository);
        String initialEntry = "a - b";
        String updatedEntry = "a - c";
        when(stubLearningItemRepository.items()).thenReturn(Collections.singletonList(LearningItem.fromSingleString(updatedEntry)));

        updatedLearningItemSaver.saveText(new TextSource.StableTextSource(updatedEntry), initialEntry);
        updatedLearningItemSaver.onItemChange(LearningItem.fromSingleString(updatedEntry));

        assertThat(updatedLearningItemSaver.savedText(), equalTo(updatedEntry));
    }

    @Test
    public void revertsUnpersistedChangesAfterASave() {
        UpdatedLearningItemSaver updatedLearningItemSaver = new UpdatedLearningItemSaver(dummyLogger, stubLearningItemRepository);
        String initialEntry = "a - b";
        String updatedEntry = "a - c";
        String nextUpdatedEntry = "q - w";
        when(stubLearningItemRepository.items()).thenReturn(Collections.singletonList(LearningItem.fromSingleString(updatedEntry)));

        updatedLearningItemSaver.saveText(new TextSource.StableTextSource(nextUpdatedEntry), initialEntry);
        updatedLearningItemSaver.onItemChange(LearningItem.fromSingleString(updatedEntry));

        assertThat(updatedLearningItemSaver.savedText(), equalTo(updatedEntry));
    }
}