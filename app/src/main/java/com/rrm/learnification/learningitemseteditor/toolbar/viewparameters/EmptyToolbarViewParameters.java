package com.rrm.learnification.learningitemseteditor.toolbar.viewparameters;

import android.support.annotation.Nullable;

import com.rrm.learnification.button.ConfigurableButton;

public class EmptyToolbarViewParameters implements ToolbarViewParameters {
    @Override
    public String toolbarTitle() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void configureFastForwardScheduleButton(ConfigurableButton fastForwardScheduleButton) {
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return false;
    }
}
