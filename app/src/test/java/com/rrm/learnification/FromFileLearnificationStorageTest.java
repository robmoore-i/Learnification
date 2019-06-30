package com.rrm.learnification;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FromFileLearnificationStorageTest {
    private AndroidLogger logger = mock(AndroidLogger.class);

    @Test
    public void onReadIfFileDoesntExistThenItCreatesIt() throws IOException {
        AndroidStorage mockAndroidStorage = mock(AndroidStorage.class);
        when(mockAndroidStorage.doesFileExist(FromFileLearnificationStorage.FILE_NAME)).thenReturn(false);
        FromFileLearnificationStorage fromFileLearnificationStorage = new FromFileLearnificationStorage(logger, mockAndroidStorage);

        fromFileLearnificationStorage.read();

        verify(mockAndroidStorage, times(1)).createNewEmptyFile(FromFileLearnificationStorage.FILE_NAME);
    }

    @Test
    public void onReadIfFileDoesntExistThenItWritesTheDefaultLearningItemsToFile() throws IOException {
        AndroidStorage mockAndroidStorage = mock(AndroidStorage.class);
        when(mockAndroidStorage.doesFileExist(FromFileLearnificationStorage.FILE_NAME)).thenReturn(false);
        FromFileLearnificationStorage fromFileLearnificationStorage = new FromFileLearnificationStorage(logger, mockAndroidStorage);

        fromFileLearnificationStorage.read();

        List<String> lines = FromFileLearnificationStorage.defaultLearningItems().stream().map(LearningItem::asSingleString).collect(Collectors.toList());
        verify(mockAndroidStorage, times(1)).appendLines(FromFileLearnificationStorage.FILE_NAME, lines);
    }

    @Test
    public void onReadItReturnsLinesReadFromFileEvenIfFileDoesntExistInitially() throws FileNotFoundException {
        AndroidStorage mockAndroidStorage = mock(AndroidStorage.class);
        when(mockAndroidStorage.doesFileExist(FromFileLearnificationStorage.FILE_NAME)).thenReturn(false);
        ArrayList<String> lines = new ArrayList<>();
        String testLearningItem = "TEST - THING";
        lines.add(testLearningItem);
        when(mockAndroidStorage.readLines(FromFileLearnificationStorage.FILE_NAME)).thenReturn(lines);
        FromFileLearnificationStorage fromFileLearnificationStorage = new FromFileLearnificationStorage(logger, mockAndroidStorage);

        List<LearningItem> learningItems = fromFileLearnificationStorage.read();
        assertThat(learningItems.size(), equalTo(1));
        assertThat(learningItems.get(0).asSingleString(), equalTo(testLearningItem));
    }

    @Test
    public void onRewriteItDeletesTheFile() {
        AndroidStorage mockAndroidStorage = mock(AndroidStorage.class);
        FromFileLearnificationStorage fromFileLearnificationStorage = new FromFileLearnificationStorage(logger, mockAndroidStorage);

        fromFileLearnificationStorage.rewrite(new ArrayList<>());

        verify(mockAndroidStorage, times(1)).deleteFile(FromFileLearnificationStorage.FILE_NAME);
    }

    @Test
    public void onRewriteItAppendsTheLines() throws IOException {
        AndroidStorage mockAndroidStorage = mock(AndroidStorage.class);
        FromFileLearnificationStorage fromFileLearnificationStorage = new FromFileLearnificationStorage(logger, mockAndroidStorage);

        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(new LearningItem("TEST", "THING"));
        fromFileLearnificationStorage.rewrite(learningItems);

        List<String> lines = learningItems.stream().map(LearningItem::asSingleString).collect(Collectors.toList());
        verify(mockAndroidStorage, times(1)).appendLines(FromFileLearnificationStorage.FILE_NAME, lines);
    }
}