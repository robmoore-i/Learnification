package com.rrm.learnification;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FromFileLearningItemStorageTest {
    private AndroidLogger logger = mock(AndroidLogger.class);

    @Test
    public void onReadIfFileDoesntExistThenItWritesTheDefaultLearningItemsToFile() throws IOException {
        FileStorageAdaptor mockFileStorageAdaptor = mock(FileStorageAdaptor.class);
        when(mockFileStorageAdaptor.doesFileExist(FromFileLearningItemStorage.LEARNING_ITEMS_FILE_NAME)).thenReturn(false);
        FromFileLearningItemStorage fromFileLearnificationStorage = new FromFileLearningItemStorage(logger, mockFileStorageAdaptor);

        fromFileLearnificationStorage.read();

        List<String> lines = FromFileLearningItemStorage.defaultLearningItems().stream().map(LearningItem::asSingleString).collect(Collectors.toList());
        verify(mockFileStorageAdaptor, times(1)).appendLines(FromFileLearningItemStorage.LEARNING_ITEMS_FILE_NAME, lines);
    }

    @Test
    public void onReadItReturnsLinesReadFromFileEvenIfFileDoesntExistInitially() throws IOException {
        FileStorageAdaptor mockFileStorageAdaptor = mock(FileStorageAdaptor.class);
        when(mockFileStorageAdaptor.doesFileExist(FromFileLearningItemStorage.LEARNING_ITEMS_FILE_NAME)).thenReturn(false);
        String testLearningItem = "TEST - THING";
        when(mockFileStorageAdaptor.readLines(FromFileLearningItemStorage.LEARNING_ITEMS_FILE_NAME)).thenReturn(Collections.singletonList(testLearningItem));
        FromFileLearningItemStorage fromFileLearnificationStorage = new FromFileLearningItemStorage(logger, mockFileStorageAdaptor);

        List<LearningItem> learningItems = fromFileLearnificationStorage.read();
        assertThat(learningItems.size(), equalTo(1));
        assertThat(learningItems.get(0).asSingleString(), equalTo(testLearningItem));
    }

    @Test
    public void onRewriteItDeletesTheFile() {
        FileStorageAdaptor mockFileStorageAdaptor = mock(FileStorageAdaptor.class);
        FromFileLearningItemStorage fromFileLearnificationStorage = new FromFileLearningItemStorage(logger, mockFileStorageAdaptor);

        fromFileLearnificationStorage.rewrite(new ArrayList<>());

        verify(mockFileStorageAdaptor, times(1)).deleteFile(FromFileLearningItemStorage.LEARNING_ITEMS_FILE_NAME);
    }

    @Test
    public void onRewriteItAppendsTheLines() throws IOException {
        FileStorageAdaptor mockFileStorageAdaptor = mock(FileStorageAdaptor.class);
        FromFileLearningItemStorage fromFileLearnificationStorage = new FromFileLearningItemStorage(logger, mockFileStorageAdaptor);

        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(new LearningItem("TEST", "THING"));
        fromFileLearnificationStorage.rewrite(learningItems);

        List<String> lines = learningItems.stream().map(LearningItem::asSingleString).collect(Collectors.toList());
        verify(mockFileStorageAdaptor, times(1)).appendLines(FromFileLearningItemStorage.LEARNING_ITEMS_FILE_NAME, lines);
    }

    @Test
    public void emptyLinesInTheLearnificationsFileAreIgnored() throws IOException {
        FileStorageAdaptor stubFileStorageAdaptor = mock(FileStorageAdaptor.class);
        when(stubFileStorageAdaptor.readLines(FromFileLearningItemStorage.LEARNING_ITEMS_FILE_NAME)).thenReturn(Collections.singletonList(""));
        FromFileLearningItemStorage fromFileLearnificationStorage = new FromFileLearningItemStorage(logger, stubFileStorageAdaptor);

        List<LearningItem> learningItems = fromFileLearnificationStorage.read();

        assertThat(learningItems.size(), equalTo(0));
    }
}