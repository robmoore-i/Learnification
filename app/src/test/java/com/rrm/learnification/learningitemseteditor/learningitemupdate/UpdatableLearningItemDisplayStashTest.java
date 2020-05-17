package com.rrm.learnification.learningitemseteditor.learningitemupdate;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.PersistentLearningItemRepository;

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
        UpdatableLearningItemTextDisplayStash updatableLearningItemDisplayCache = new UpdatableLearningItemTextDisplayStash(dummyLogger, mockLearningItemRepository);
        LearningItem savedLearningItem = new LearningItem("a", "b", "default");
        LearningItem updatedLearningItem = new LearningItem("a", "c", "default");
        when(mockLearningItemRepository.get(any())).thenReturn(savedLearningItem);

        updatableLearningItemDisplayCache.saveText(new TextSource.StableTextSource(updatedLearningItem.toDisplayString().toString()), savedLearningItem.toDisplayString().toString());

        assertThat(updatableLearningItemDisplayCache.savedText(), equalTo(savedLearningItem.toDisplayString().toString()));
    }

    @Test
    public void savesPersistedChanges() {
        UpdatableLearningItemTextDisplayStash updatableLearningItemDisplayCache = new UpdatableLearningItemTextDisplayStash(dummyLogger, mockLearningItemRepository);
        LearningItem savedLearningItem = new LearningItem("a", "b", "default");
        LearningItemText updatedLearningItemText = new LearningItemText("a", "c");
        when(mockLearningItemRepository.get(any())).thenReturn(savedLearningItem);

        updatableLearningItemDisplayCache.saveText(new TextSource.StableTextSource(updatedLearningItemText.toString()), savedLearningItem.toDisplayString().toString());
        updatableLearningItemDisplayCache.onItemChange(updatedLearningItemText);

        assertThat(updatableLearningItemDisplayCache.savedText(), equalTo(updatedLearningItemText.toString()));
    }

    @Test
    public void revertsUnpersistedChangesAfterASave() {
        UpdatableLearningItemTextDisplayStash updatableLearningItemDisplayCache = new UpdatableLearningItemTextDisplayStash(dummyLogger, mockLearningItemRepository);
        LearningItem savedLearningItem = new LearningItem("a", "b", "default");
        LearningItemText updatedLearningItemText = new LearningItemText("a", "c");
        String nextUpdatedTextEntry = "q - w";
        when(mockLearningItemRepository.get(any())).thenReturn(savedLearningItem);

        updatableLearningItemDisplayCache.saveText(new TextSource.StableTextSource(nextUpdatedTextEntry), savedLearningItem.toDisplayString().toString());
        updatableLearningItemDisplayCache.onItemChange(updatedLearningItemText);

        assertThat(updatableLearningItemDisplayCache.savedText(), equalTo(updatedLearningItemText.toString()));
    }

    @Test
    public void itGetsTheSavedLearningItemFromTheLearningItemRepository() {
        Runnable mock = mock(Runnable.class);
        LearningItem savedLearningItem = new LearningItem("a", "b", "default");
        LearningItem updatedLearningItem = new LearningItem("a", "c", "Chinese");
        when(mockLearningItemRepository.get(any())).thenReturn(savedLearningItem);
        UpdatableLearningItemTextDisplayStash stash = new UpdatableLearningItemTextDisplayStash(dummyLogger, mockLearningItemRepository);

        // The text source tells that the text has been updated
        TextSource.StableTextSource textSource = new TextSource.StableTextSource(updatedLearningItem.toDisplayString().toString());
        stash.saveText(textSource, savedLearningItem.toDisplayString().toString());

        // We commit the stashed value using our custom updater, which runs assertions on the stash contents.
        stash.commit((target, replacement) -> {
            assertThat(target, equalTo(savedLearningItem.toDisplayString()));
            assertThat(replacement, equalTo(updatedLearningItem.toDisplayString()));
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
        UpdatableLearningItemTextDisplayStash stash = new UpdatableLearningItemTextDisplayStash(dummyLogger, mockLearningItemRepository);

        // The text source tells that the text has been updated
        TextSource.StableTextSource textSource = new TextSource.StableTextSource(updatedLearningItem.toDisplayString().toString());
        stash.saveText(textSource, savedLearningItem.toDisplayString().toString());

        // We commit the stashed value using our custom updater, which runs assertions on the stash contents.
        stash.commit((target, replacement) -> {
            assertThat(target, equalTo(savedLearningItem.toDisplayString()));
            assertThat(replacement, equalTo(updatedLearningItem.toDisplayString()));
            mock.run();
        });
        verify(mock).run();
    }
}