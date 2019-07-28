package com.rrm.learnification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
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
        PeriodicityPicker periodicityPicker = new PeriodicityPicker(logger, settingsActivityView);
        periodicityPicker.setInputRangeInMinutes(5, 90);
        periodicityPicker.setOnValuePickedListener(new StorePeriodicityOnValuePickedCommand(logger, settingsRepository));
        periodicityPicker.setToValue(settingsRepository.getInitialPeriodicityPickerValue());
        periodicityPicker.setChoiceFormatter();
    }
}
