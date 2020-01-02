package com.rrm.learnification.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.rrm.learnification.R;
import com.rrm.learnification.button.FinishActivityOnClickCommand;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.learnificationdelay.DelayPicker;
import com.rrm.learnification.settings.learnificationdelay.SaveDelayFromPickerOnClickCommand;
import com.rrm.learnification.settings.learnificationdelay.StoreDelayOnValuePickedCommand;
import com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategyRadioGroup;
import com.rrm.learnification.settings.learnificationpromptstrategy.SavePromptStrategyOnClickCommand;
import com.rrm.learnification.settings.save.SaveSettingsButton;
import com.rrm.learnification.settings.save.SaveSettingsView;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.toolbar.AppToolbar;

import static java.lang.Integer.max;

public class SettingsActivity extends AppCompatActivity implements SaveSettingsView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        AndroidLogger logger = new AndroidLogger();

        SettingsActivityView settingsActivityView = new SettingsActivityView(this);

        AppToolbar appToolbar = new AppToolbar(logger, settingsActivityView);
        appToolbar.setTitle("Settings");

        FileStorageAdaptor fileStorageAdaptor = new AndroidInternalStorageAdaptor(logger, this);
        SettingsRepository settingsRepository = new SettingsRepository(logger, fileStorageAdaptor);

        DelayPicker delayPicker = new DelayPicker(logger, settingsActivityView);
        delayPicker.setInputRangeInMinutes(1, 180);
        delayPicker.setOnValuePickedListener(new StoreDelayOnValuePickedCommand(logger, settingsRepository));
        delayPicker.setToValue(max(settingsRepository.readDelayMinutes(), 1));
        delayPicker.setChoiceFormatter();

        LearnificationPromptStrategyRadioGroup learnificationPromptStrategyRadioGroup = new LearnificationPromptStrategyRadioGroup(logger, settingsActivityView);
        learnificationPromptStrategyRadioGroup.setValue(settingsRepository.readLearnificationPromptStrategy());

        SaveSettingsButton saveSettingsButton = new SaveSettingsButton(logger, this);
        saveSettingsButton.addOnClickHandler(new SaveDelayFromPickerOnClickCommand(logger, settingsRepository, delayPicker));
        saveSettingsButton.addOnClickHandler(new FinishActivityOnClickCommand(this));
        saveSettingsButton.addOnClickHandler(new SavePromptStrategyOnClickCommand(settingsRepository, learnificationPromptStrategyRadioGroup));
    }

    @Override
    public Button saveSettingsButton() {
        return findViewById(R.id.save_settings_button);
    }
}
