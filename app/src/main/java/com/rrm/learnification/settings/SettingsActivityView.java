package com.rrm.learnification.settings;

import android.annotation.SuppressLint;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import com.rrm.learnification.R;
import com.rrm.learnification.radiogroup.RadioGroupMappings;
import com.rrm.learnification.radiogroup.RadioGroupView;
import com.rrm.learnification.settings.learnificationdelay.DelayPickerView;
import com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy;
import com.rrm.learnification.toolbar.SimpleToolbarView;

import java.util.HashMap;

import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.LEFT_TO_RIGHT;
import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.MIXED;
import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.RIGHT_TO_LEFT;

public class SettingsActivityView implements SimpleToolbarView, DelayPickerView, RadioGroupView<LearnificationPromptStrategy> {
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
    public void checkOption(int radioButtonViewId) {
        learnificationPromptStrategyRadioGroup().check(radioButtonViewId);
    }

    @Override
    public int viewIdOfCheckedOption() {
        return learnificationPromptStrategyRadioGroup().getCheckedRadioButtonId();
    }

    @Override
    public RadioGroupMappings<LearnificationPromptStrategy> radioGroupMappings() {
        // Maps viewId -> option object
        @SuppressLint("UseSparseArrays")
        HashMap<Integer, LearnificationPromptStrategy> options = new HashMap<>();
        options.put(R.id.left_to_right, LEFT_TO_RIGHT);
        options.put(R.id.right_to_left, RIGHT_TO_LEFT);
        options.put(R.id.mixed_left_and_right, MIXED);

        // Maps option object -> viewId
        HashMap<LearnificationPromptStrategy, Integer> viewIds = new HashMap<>();
        viewIds.put(LEFT_TO_RIGHT, R.id.left_to_right);
        viewIds.put(RIGHT_TO_LEFT, R.id.right_to_left);
        viewIds.put(MIXED, R.id.mixed_left_and_right);

        return new RadioGroupMappings<>(options, viewIds);
    }

    @Override
    public void bindActionsToRadioGroupOptions(RadioGroupMappings<LearnificationPromptStrategy> radioGroupMappings) {
        learnificationPromptStrategyRadioGroup().setOnCheckedChangeListener((group, checkedId) -> radioGroupMappings.onChecked(checkedId));
    }

    private RadioGroup learnificationPromptStrategyRadioGroup() {
        return activity.findViewById(R.id.learnification_prompt_strategy_radio_group);
    }
}
