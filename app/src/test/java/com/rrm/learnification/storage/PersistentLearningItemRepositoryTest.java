package com.rrm.learnification.storage;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersistentLearningItemRepositoryTest {
    private final AndroidLogger androidLogger = mock(AndroidLogger.class);
    private final LearningItemTextUpdateBroker dummyItemUpdateBroker = mock(LearningItemTextUpdateBroker.class);
    private final SqlLearningItemSetRecordStore stubLearningPersistentItemStore = mock(SqlLearningItemSetRecordStore.class);

    @Before
    public void beforeEach() {
        when(stubLearningPersistentItemStore.applySet(any())).thenAnswer((Answer<LearningItem>) invocation -> ((LearningItemText) invocation.getArguments()[0]).withSet("default"));
    }

    @Test
    public void canAddLearningItems() {
        LearningItemText newLearningItemText = new LearningItemText("L", "R");
        when(stubLearningPersistentItemStore.readAll()).thenReturn(new ArrayList<>());
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(androidLogger, stubLearningPersistentItemStore, dummyItemUpdateBroker);

        persistentLearnificationRepository.add(newLearningItemText);

        List<LearningItem> learningItems = persistentLearnificationRepository.items();
        assertEquals(1, learningItems.size());
        assertEquals("L", learningItems.get(0).left);
        assertEquals("R", learningItems.get(0).right);
    }

    @Test
    public void returnsLearningItemsWithLatestEntryFirst() {
        when(stubLearningPersistentItemStore.readAll()).thenReturn(new ArrayList<>());
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(androidLogger, stubLearningPersistentItemStore, dummyItemUpdateBroker);

        persistentLearnificationRepository.add(new LearningItemText("L1", "R1"));
        persistentLearnificationRepository.add(new LearningItemText("L2", "R2"));
        persistentLearnificationRepository.add(new LearningItemText("L3", "R3"));

        List<LearningItem> learningItems = persistentLearnificationRepository.items();
        assertEquals("L3", learningItems.get(0).left);
        assertEquals("R3", learningItems.get(0).right);
    }

    @Test
    public void removesItemCorrespondingToIndexInReturnedList() {
        when(stubLearningPersistentItemStore.readAll()).thenReturn(new ArrayList<>(Arrays.asList(
                new LearningItem("a", "a", "default"),
                new LearningItem("b", "b", "default"),
                new LearningItem("c", "c", "default"),
                new LearningItem("d", "d", "default")
        )));
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(new AndroidLogger() {
            @Override
            public void v(String tag, String message) {
                System.out.println(tag + " - " + message);
            }
        }, stubLearningPersistentItemStore, dummyItemUpdateBroker);

        List<LearningItem> items = persistentLearnificationRepository.items();
        int removalIndex = 2;
        LearningItem expectedDeletedLearningItem = items.get(removalIndex);
        persistentLearnificationRepository.removeAt(removalIndex);

        assertThat(persistentLearnificationRepository.items(), not(hasItem(expectedDeletedLearningItem)));
    }

    @Test
    public void gettingItemsTwiceDoesntChangeThem() {
        ArrayList<LearningItem> learningItems = new ArrayList<>(Arrays.asList(
                new LearningItem("a", "a", "default"),
                new LearningItem("b", "b", "default"),
                new LearningItem("c", "c", "default"),
                new LearningItem("d", "d", "default")
        ));
        when(stubLearningPersistentItemStore.readAll()).thenReturn(learningItems);
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(androidLogger, stubLearningPersistentItemStore, dummyItemUpdateBroker);

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
                new LearningItem("a", "a", "default"),
                new LearningItem("b", "b", "default"),
                new LearningItem("c", "c", "default"),
                new LearningItem("d", "d", "default")
        ));
        when(stubLearningPersistentItemStore.readAll()).thenReturn(learningItems);
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(androidLogger, stubLearningPersistentItemStore, dummyItemUpdateBroker);

        persistentLearnificationRepository.replace(new LearningItemText("c", "c"), new LearningItemText("e", "e"));

        List<LearningItem> items = persistentLearnificationRepository.items();
        assertThat(items, hasItem(new LearningItem("e", "e", "default")));
        assertThat(items, not(hasItem(new LearningItem("c", "c", "default"))));
    }

    @Test
    public void canGetLearningItemFromLearningItemDisplayString() {
        ArrayList<LearningItem> learningItems = new ArrayList<>(Arrays.asList(
                new LearningItem("a", "a", "default"),
                new LearningItem("b", "b", "default"),
                new LearningItem("c", "c", "default"),
                new LearningItem("d", "d", "default")
        ));
        when(stubLearningPersistentItemStore.readAll()).thenReturn(learningItems);
        PersistentLearningItemRepository repository = new PersistentLearningItemRepository(androidLogger, stubLearningPersistentItemStore, dummyItemUpdateBroker);

        LearningItem learningItem = repository.get(new LearningItemText("b", "b"));

        assertEquals(new LearningItem("b", "b", "default"), learningItem);
    }
}