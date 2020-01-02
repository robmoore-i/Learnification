package com.rrm.learnification.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.rrm.learnification.R;
import com.rrm.learnification.button.FinishActivityOnClickCommand;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.AndroidInternalStorageAdaptor;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.toolbar.AppToolbar;

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
        delayPicker.setToValue(settingsRepository.getInitialLearnificationDelayPickerValue());
        delayPicker.setChoiceFormatter();

        SaveSettingsButton saveSettingsButton = new SaveSettingsButton(logger, this);
        saveSettingsButton.addOnClickHandler(new SaveDelayFromPickerOnClickCommand(logger, settingsRepository, delayPicker));
        saveSettingsButton.addOnClickHandler(new FinishActivityOnClickCommand(this));
    }

    @Override
    public Button saveSettingsButton() {
        return findViewById(R.id.save_settings_button);
    }
}
