package com.rrm.learnification.settings;

import android.support.v7.widget.Toolbar;
import android.widget.NumberPicker;

import com.rrm.learnification.R;
import com.rrm.learnification.common.ToolbarView;

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
