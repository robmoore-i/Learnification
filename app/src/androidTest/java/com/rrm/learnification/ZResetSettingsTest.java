package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.storage.FileStorageAdaptor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ZResetSettingsTest {
    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);

    @Test
    public void resetSettings() {
        FileStorageAdaptor fileStorageAdaptor = activityTestRule.getActivity().androidTestObjectFactory().getFileStorageAdaptor();
        fileStorageAdaptor.deleteFile(SettingsRepository.LEARNIFICATION_DELAY_FILE_NAME);
        fileStorageAdaptor.deleteFile(SettingsRepository.LEARNIFICATION_PROMPT_STRATEGY_FILE_NAME);
    }
}
