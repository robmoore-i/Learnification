package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.PersistentLearningItemRepository;
import com.rrm.learnification.textlist.TextSource;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpdatableLearningItemDisplayStashTest {
    private final PersistentLearningItemRepository mockLearningItemRepository = mock(PersistentLearningItemRepository.class);
    private final AndroidLogger dummyLogger = mock(AndroidLogger.class);

    @Test
    public void revertsUnpersistedChanges() {
        UpdatableLearningItemDisplayStash updatableLearningItemDisplayCache = new UpdatableLearningItemDisplayStash(dummyLogger, mockLearningItemRepository);
        LearningItem savedLearningItem = new LearningItem("a", "b", "default");
        LearningItem updatedLearningItem = new LearningItem("a", "c", "default");
        when(mockLearningItemRepository.get(any())).thenReturn(savedLearningItem);

        updatableLearningItemDisplayCache.saveText(new TextSource.StableTextSource(updatedLearningItem.toDisplayString()), savedLearningItem.toDisplayString());

        assertThat(updatableLearningItemDisplayCache.savedText(), equalTo(savedLearningItem.toDisplayString()));
    }

    @Test
    public void savesPersistedChanges() {
        UpdatableLearningItemDisplayStash updatableLearningItemDisplayCache = new UpdatableLearningItemDisplayStash(dummyLogger, mockLearningItemRepository);
        LearningItem savedLearningItem = new LearningItem("a", "b", "default");
        LearningItem updatedLearningItem = new LearningItem("a", "c", "default");
        when(mockLearningItemRepository.get(any())).thenReturn(savedLearningItem);

        updatableLearningItemDisplayCache.saveText(new TextSource.StableTextSource(updatedLearningItem.toDisplayString()), savedLearningItem.toDisplayString());
        updatableLearningItemDisplayCache.onItemChange(updatedLearningItem);

        assertThat(updatableLearningItemDisplayCache.savedText(), equalTo(updatedLearningItem.toDisplayString()));
    }

    @Test
    public void revertsUnpersistedChangesAfterASave() {
        UpdatableLearningItemDisplayStash updatableLearningItemDisplayCache = new UpdatableLearningItemDisplayStash(dummyLogger, mockLearningItemRepository);
        LearningItem savedLearningItem = new LearningItem("a", "b", "default");
        LearningItem updatedLearningItem = new LearningItem("a", "c", "default");
        String nextUpdatedTextEntry = "q - w";
        when(mockLearningItemRepository.get(any())).thenReturn(savedLearningItem);

        updatableLearningItemDisplayCache.saveText(new TextSource.StableTextSource(nextUpdatedTextEntry), savedLearningItem.toDisplayString());
        updatableLearningItemDisplayCache.onItemChange(updatedLearningItem);

        assertThat(updatableLearningItemDisplayCache.savedText(), equalTo(updatedLearningItem.toDisplayString()));
    }

    @Test
    public void itGetsTheSavedLearningItemFromTheLearningItemRepository() {
        Runnable mock = mock(Runnable.class);
        LearningItem savedLearningItem = new LearningItem("a", "b", "default");
        LearningItem updatedLearningItem = new LearningItem("a", "c", "Chinese");
        when(mockLearningItemRepository.get(any())).thenReturn(savedLearningItem);
        UpdatableLearningItemDisplayStash stash = new UpdatableLearningItemDisplayStash(dummyLogger, mockLearningItemRepository);

        // The text source tells that the text has been updated
        TextSource.StableTextSource textSource = new TextSource.StableTextSource(updatedLearningItem.toDisplayString());
        stash.saveText(textSource, savedLearningItem.toDisplayString());

        // We commit the stashed value using our custom updater, which runs assertions on the stash contents.
        stash.commit((target, replacement) -> {
            assertThat(target, equalTo(savedLearningItem));
            assertThat(replacement.apply("Chinese"), equalTo(updatedLearningItem));
            mock.run();
        });
        verify(mock).run();
    }

    @Test
    public void itGetsTheSavedLearningItemFromTheLearningItemRepositoryForANonDefaultLearningItemSet() {
        Runnable mock = mock(Runnable.class);
        LearningItem savedLearningItem = new LearningItem("a", "b", "Thai");
        LearningItem updatedLearningItem = new LearningItem("a", "c", "Chinese");
        when(mockLearningItemRepository.get(any())).thenReturn(savedLearningItem);
        UpdatableLearningItemDisplayStash stash = new UpdatableLearningItemDisplayStash(dummyLogger, mockLearningItemRepository);

        // The text source tells that the text has been updated
        TextSource.StableTextSource textSource = new TextSource.StableTextSource(updatedLearningItem.toDisplayString());
        stash.saveText(textSource, savedLearningItem.toDisplayString());

        // We commit the stashed value using our custom updater, which runs assertions on the stash contents.
        stash.commit((target, replacement) -> {
            assertThat(target, equalTo(savedLearningItem));
            assertThat(replacement.apply("Chinese"), equalTo(updatedLearningItem));
            mock.run();
        });
        verify(mock).run();
    }
}