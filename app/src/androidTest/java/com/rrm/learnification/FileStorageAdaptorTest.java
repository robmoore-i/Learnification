package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.FileStorageAdaptor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class FileStorageAdaptorTest {
    private static final String TEST_FILE_NAME = "test_file";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private FileStorageAdaptor fileStorageAdaptor;

    @Before
    public void beforeEach() {
        fileStorageAdaptor = activityTestRule.getActivity().getFileStorageAdaptor();
    }

    @After
    public void afterEach() {
        fileStorageAdaptor.deleteFile(TEST_FILE_NAME);
    }

    @AfterClass
    public static void tearDown() {
    }

    @Test
    public void canWriteAndReadAOneLineFile() throws IOException {
        String line = "a line";
        fileStorageAdaptor.appendLines(TEST_FILE_NAME, Collections.singletonList(line));
        List<String> lines = fileStorageAdaptor.readLines(TEST_FILE_NAME);

        assertThat(lines.get(0), equalTo(line));
    }

    @Test
    public void canWriteAndReadATwoLineFile() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("line 1");
        lines.add("line 2");
        fileStorageAdaptor.appendLines(TEST_FILE_NAME, lines);
        List<String> readLines = fileStorageAdaptor.readLines(TEST_FILE_NAME);

        assertThat(readLines.get(0), equalTo(lines.get(0)));
        assertThat(readLines.get(1), equalTo(lines.get(1)));
    }

    @Test
    public void canWriteAndReadATwoLineUsingTwoCalls() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("line 1");
        lines.add("line 2");
        fileStorageAdaptor.appendLines(TEST_FILE_NAME, Collections.singletonList(lines.get(0)));
        fileStorageAdaptor.appendLines(TEST_FILE_NAME, Collections.singletonList(lines.get(1)));
        List<String> readLines = fileStorageAdaptor.readLines(TEST_FILE_NAME);

        assertThat(readLines.get(0), equalTo(lines.get(0)));
        assertThat(readLines.get(1), equalTo(lines.get(1)));
    }

    @Test
    public void canOverwriteFile() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("line 1");
        lines.add("line 2");
        fileStorageAdaptor.appendLines(TEST_FILE_NAME, Collections.singletonList(lines.get(0)));
        fileStorageAdaptor.overwriteLines(TEST_FILE_NAME, Collections.singletonList(lines.get(1)));
        List<String> readLines = fileStorageAdaptor.readLines(TEST_FILE_NAME);

        assertThat(readLines.get(0), equalTo(lines.get(1)));
    }
}
