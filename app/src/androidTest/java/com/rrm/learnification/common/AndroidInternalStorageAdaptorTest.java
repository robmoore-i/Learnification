package com.rrm.learnification.common;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;

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
public class AndroidInternalStorageAdaptorTest {
    private static final String TEST_FILE_NAME = "test_file";
    private static final TestJanitor testJanitor = new TestJanitor();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private AndroidInternalStorageAdaptor androidInternalStorageAdaptor;

    @Before
    public void beforeEach() {
        androidInternalStorageAdaptor = activityTestRule.getActivity().getAndroidInternalStorageAdaptor();
    }

    @After
    public void afterEach() {
        androidInternalStorageAdaptor.deleteFile(TEST_FILE_NAME);
    }

    @AfterClass
    public static void tearDown() {
        testJanitor.clearApp();
    }

    @Test
    public void canWriteAndReadAOneLineFile() throws IOException {
        String line = "a line";
        androidInternalStorageAdaptor.appendLines(TEST_FILE_NAME, Collections.singletonList(line));
        List<String> lines = androidInternalStorageAdaptor.readLines(TEST_FILE_NAME);

        assertThat(lines.get(0), equalTo(line));
    }

    @Test
    public void canWriteAndReadATwoLineFile() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("line 1");
        lines.add("line 2");
        androidInternalStorageAdaptor.appendLines(TEST_FILE_NAME, lines);
        List<String> readLines = androidInternalStorageAdaptor.readLines(TEST_FILE_NAME);

        assertThat(readLines.get(0), equalTo(lines.get(0)));
        assertThat(readLines.get(1), equalTo(lines.get(1)));
    }

    @Test
    public void canWriteAndReadATwoLineUsingTwoCalls() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("line 1");
        lines.add("line 2");
        androidInternalStorageAdaptor.appendLines(TEST_FILE_NAME, Collections.singletonList(lines.get(0)));
        androidInternalStorageAdaptor.appendLines(TEST_FILE_NAME, Collections.singletonList(lines.get(1)));
        List<String> readLines = androidInternalStorageAdaptor.readLines(TEST_FILE_NAME);

        assertThat(readLines.get(0), equalTo(lines.get(0)));
        assertThat(readLines.get(1), equalTo(lines.get(1)));
    }

    @Test
    public void canOverwriteFile() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("line 1");
        lines.add("line 2");
        androidInternalStorageAdaptor.appendLines(TEST_FILE_NAME, Collections.singletonList(lines.get(0)));
        androidInternalStorageAdaptor.overwriteLines(TEST_FILE_NAME, Collections.singletonList(lines.get(1)));
        List<String> readLines = androidInternalStorageAdaptor.readLines(TEST_FILE_NAME);

        assertThat(readLines.get(0), equalTo(lines.get(1)));
    }
}
