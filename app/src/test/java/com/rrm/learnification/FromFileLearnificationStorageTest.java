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

public class FromFileLearnificationStorageTest {
    private AndroidLogger logger = mock(AndroidLogger.class);

    @Test
    public void onReadIfFileDoesntExistThenItWritesTheDefaultLearningItemsToFile() throws IOException {
        AndroidInternalStorageAdaptor mockAndroidInternalStorageAdaptor = mock(AndroidInternalStorageAdaptor.class);
        when(mockAndroidInternalStorageAdaptor.doesFileExist(FromFileLearnificationStorage.FILE_NAME)).thenReturn(false);
        FromFileLearnificationStorage fromFileLearnificationStorage = new FromFileLearnificationStorage(logger, mockAndroidInternalStorageAdaptor);

        fromFileLearnificationStorage.read();

        List<String> lines = FromFileLearnificationStorage.defaultLearningItems().stream().map(LearningItem::asSingleString).collect(Collectors.toList());
        verify(mockAndroidInternalStorageAdaptor, times(1)).appendLines(FromFileLearnificationStorage.FILE_NAME, lines);
    }

    @Test
    public void onReadItReturnsLinesReadFromFileEvenIfFileDoesntExistInitially() throws IOException {
        AndroidInternalStorageAdaptor mockAndroidInternalStorageAdaptor = mock(AndroidInternalStorageAdaptor.class);
        when(mockAndroidInternalStorageAdaptor.doesFileExist(FromFileLearnificationStorage.FILE_NAME)).thenReturn(false);
        String testLearningItem = "TEST - THING";
        when(mockAndroidInternalStorageAdaptor.readLines(FromFileLearnificationStorage.FILE_NAME)).thenReturn(Collections.singletonList(testLearningItem));
        FromFileLearnificationStorage fromFileLearnificationStorage = new FromFileLearnificationStorage(logger, mockAndroidInternalStorageAdaptor);

        List<LearningItem> learningItems = fromFileLearnificationStorage.read();
        assertThat(learningItems.size(), equalTo(1));
        assertThat(learningItems.get(0).asSingleString(), equalTo(testLearningItem));
    }

    @Test
    public void onRewriteItDeletesTheFile() {
        AndroidInternalStorageAdaptor mockAndroidInternalStorageAdaptor = mock(AndroidInternalStorageAdaptor.class);
        FromFileLearnificationStorage fromFileLearnificationStorage = new FromFileLearnificationStorage(logger, mockAndroidInternalStorageAdaptor);

        fromFileLearnificationStorage.rewrite(new ArrayList<>());

        verify(mockAndroidInternalStorageAdaptor, times(1)).deleteFile(FromFileLearnificationStorage.FILE_NAME);
    }

    @Test
    public void onRewriteItAppendsTheLines() throws IOException {
        AndroidInternalStorageAdaptor mockAndroidInternalStorageAdaptor = mock(AndroidInternalStorageAdaptor.class);
        FromFileLearnificationStorage fromFileLearnificationStorage = new FromFileLearnificationStorage(logger, mockAndroidInternalStorageAdaptor);

        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(new LearningItem("TEST", "THING"));
        fromFileLearnificationStorage.rewrite(learningItems);

        List<String> lines = learningItems.stream().map(LearningItem::asSingleString).collect(Collectors.toList());
        verify(mockAndroidInternalStorageAdaptor, times(1)).appendLines(FromFileLearnificationStorage.FILE_NAME, lines);
    }

    @Test
    public void emptyLinesInTheLearnificationsFileAreIgnored() throws IOException {
        AndroidInternalStorageAdaptor stubAndroidInternalStorageAdaptor = mock(AndroidInternalStorageAdaptor.class);
        when(stubAndroidInternalStorageAdaptor.readLines(FromFileLearnificationStorage.FILE_NAME)).thenReturn(Collections.singletonList(""));
        FromFileLearnificationStorage fromFileLearnificationStorage = new FromFileLearnificationStorage(logger, stubAndroidInternalStorageAdaptor);

        List<LearningItem> learningItems = fromFileLearnificationStorage.read();

        assertThat(learningItems.size(), equalTo(0));
    }
}