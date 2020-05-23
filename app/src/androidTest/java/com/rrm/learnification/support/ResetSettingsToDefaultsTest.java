package com.rrm.learnification.support;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.files.FileStorageAdaptor;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ResetSettingsToDefaultsTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    @Test
    public void resetSettings() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        FileStorageAdaptor fileStorageAdaptor = androidTestObjectFactory.getFileStorageAdaptor();
        fileStorageAdaptor.deleteFile(SettingsRepository.LEARNIFICATION_DELAY_FILE_NAME);
        fileStorageAdaptor.deleteFile(SettingsRepository.LEARNIFICATION_PROMPT_STRATEGY_FILE_NAME);
    }
}
