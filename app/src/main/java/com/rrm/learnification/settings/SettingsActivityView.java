package com.rrm.learnification.settings;

import android.widget.NumberPicker;

import com.rrm.learnification.R;
import com.rrm.learnification.toolbar.SimpleToolbarView;

class SettingsActivityView implements SimpleToolbarView, PeriodicityPickerView {
    private final SettingsActivity activity;

    SettingsActivityView(SettingsActivity activity) {
        this.activity = activity;
    }

    @Override
    public void updateToolbar(String title) {
        activity.setTitle(title);
    }

    @Override
    public NumberPicker periodicityPicker() {
        return activity.findViewById(R.id.periodicity_picker);
    }
}
