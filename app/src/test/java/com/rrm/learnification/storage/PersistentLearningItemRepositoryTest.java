package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersistentLearningItemRepositoryTest {
    private final AndroidLogger androidLogger = mock(AndroidLogger.class);
    private final LearningItemUpdateBroker dummyItemUpdateBroker = mock(LearningItemUpdateBroker.class);
    @SuppressWarnings("unchecked")
    private final ItemStorage<LearningItem> stubLearningItemStorage = mock(ItemStorage.class);

    @Test
    public void canAddLearningItems() {
        when(stubLearningItemStorage.read()).thenReturn(new ArrayList<>());
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(androidLogger, stubLearningItemStorage, dummyItemUpdateBroker);

        persistentLearnificationRepository.add(new LearningItem("L", "R"));

        List<LearningItem> learningItems = persistentLearnificationRepository.items();
        assertThat(learningItems.size(), equalTo(1));
        assertThat(learningItems.get(0).left, equalTo("L"));
        assertThat(learningItems.get(0).right, equalTo("R"));
    }

    @Test
    public void returnsLearningItemsWithLatestEntryFirst() {
        when(stubLearningItemStorage.read()).thenReturn(new ArrayList<>());
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(androidLogger, stubLearningItemStorage, dummyItemUpdateBroker);

        persistentLearnificationRepository.add(new LearningItem("L1", "R1"));
        persistentLearnificationRepository.add(new LearningItem("L2", "R2"));
        persistentLearnificationRepository.add(new LearningItem("L3", "R3"));

        List<LearningItem> learningItems = persistentLearnificationRepository.items();
        assertThat(learningItems.get(0).left, equalTo("L3"));
        assertThat(learningItems.get(0).right, equalTo("R3"));
    }

    @Test
    public void removesItemCorrespondingToIndexInReturnedList() {
        when(stubLearningItemStorage.read()).thenReturn(new ArrayList<>(Arrays.asList(
                new LearningItem("a", "a"),
                new LearningItem("b", "b"),
                new LearningItem("c", "c"),
                new LearningItem("d", "d")
        )));
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(new AndroidLogger() {
            @Override
            public void v(String tag, String message) {
                System.out.println(tag + " - " + message);
            }
        }, stubLearningItemStorage, dummyItemUpdateBroker);

        List<LearningItem> items = persistentLearnificationRepository.items();
        int removalIndex = 2;
        LearningItem expectedDeletedLearningItem = items.get(removalIndex);
        persistentLearnificationRepository.removeAt(removalIndex);

        assertThat(persistentLearnificationRepository.items(), not(hasItem(expectedDeletedLearningItem)));
    }

    @Test
    public void gettingItemsTwiceDoesntChangeThem() {
        ArrayList<LearningItem> learningItems = new ArrayList<>(Arrays.asList(
                new LearningItem("a", "a"),
                new LearningItem("b", "b"),
                new LearningItem("c", "c"),
                new LearningItem("d", "d")
        ));
        when(stubLearningItemStorage.read()).thenReturn(learningItems);
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(androidLogger, stubLearningItemStorage, dummyItemUpdateBroker);

        List<LearningItem> items = persistentLearnificationRepository.items();
        for (LearningItem learningItem : learningItems) {
            assertThat(items, hasItem(learningItem));
        }

        List<LearningItem> itemsAgain = persistentLearnificationRepository.items();
        for (LearningItem learningItem : learningItems) {
            assertThat(itemsAgain, hasItem(learningItem));
        }
    }

    @Test
    public void replacingALearningItemUpdatesItWhenYouReadAllItems() {
        ArrayList<LearningItem> learningItems = new ArrayList<>(Arrays.asList(
                new LearningItem("a", "a"),
                new LearningItem("b", "b"),
                new LearningItem("c", "c"),
                new LearningItem("d", "d")
        ));
        when(stubLearningItemStorage.read()).thenReturn(learningItems);
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(androidLogger, stubLearningItemStorage, dummyItemUpdateBroker);

        persistentLearnificationRepository.replace(new LearningItem("c", "c"), new LearningItem("e", "e"));

        List<LearningItem> items = persistentLearnificationRepository.items();
        assertThat(items, hasItem(new LearningItem("e", "e")));
        assertThat(items, not(hasItem(new LearningItem("c", "c"))));
    }
}