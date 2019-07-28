package com.rrm.learnification;

import android.support.v7.widget.Toolbar;
import android.widget.NumberPicker;

class SettingsActivityView implements ToolbarView, PeriodicityPickerView {
    private SettingsActivity activity;

    SettingsActivityView(SettingsActivity activity) {
        this.activity = activity;
    }

    @Override
    public Toolbar toolbar() {
        return activity.findViewById(R.id.toolbar);
    }

    @Override
    public NumberPicker periodicityPicker() {
        return activity.findViewById(R.id.periodicity_picker);
    }
}
