package com.rrm.learnification.settings;

import android.util.SparseArray;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import com.rrm.learnification.R;
import com.rrm.learnification.radiogroup.RadioGroupChangeListener;
import com.rrm.learnification.toolbar.SimpleToolbarView;

import static com.rrm.learnification.settings.LearnificationPromptStrategy.LEFT_TO_RIGHT;
import static com.rrm.learnification.settings.LearnificationPromptStrategy.MIXED;
import static com.rrm.learnification.settings.LearnificationPromptStrategy.RIGHT_TO_LEFT;

class SettingsActivityView implements SimpleToolbarView, DelayPickerView, LearnificationPromptStrategyRadioGroupView {
    private final SettingsActivity activity;

    SettingsActivityView(SettingsActivity activity) {
        this.activity = activity;
    }

    @Override
    public void updateToolbar(String title) {
        activity.setTitle(title);
    }

    @Override
    public NumberPicker delayPicker() {
        return activity.findViewById(R.id.delay_picker);
    }

    @Override
    public SparseArray<LearnificationPromptStrategy> learnificationPromptStrategyRadioGroupOptions() {
        SparseArray<LearnificationPromptStrategy> options = new SparseArray<>();
        options.put(R.id.left_to_right, LEFT_TO_RIGHT);
        options.put(R.id.right_to_left, RIGHT_TO_LEFT);
        options.put(R.id.mixed_left_and_right, MIXED);
        return options;
    }

    @Override
    public void bindLearnificationPromptStrategyRadioGroup(RadioGroupChangeListener<LearnificationPromptStrategy> radioGroupChangeListener) {
        RadioGroup radioGroup = activity.findViewById(R.id.learnification_prompt_strategy_radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> radioGroupChangeListener.onChecked(checkedId));
    }
}
